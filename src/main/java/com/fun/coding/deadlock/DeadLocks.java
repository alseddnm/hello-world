package com.fun.coding.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by nizar on 1/11/18.
 */
@Scope(value = "singleton")
@Component
public class DeadLocks {

  private static Logger LOGGER = LoggerFactory.getLogger(DeadLocks.class);
  private final int monitorEvery = 5; // run the monitor service every 5 seconds. Should be configurable.
  private ScheduledExecutorService monitorService = Executors.newScheduledThreadPool(1);
  private Object lock1 = new Object();
  private Object lock2 = new Object();


  private Semaphore threadLock = new Semaphore(1);

  /**
   *
   */
  private Thread thread1 = new Thread(new Runnable() {
    @Override
    public void run() {
      synchronized (lock1) {
        LOGGER.info("Thread1 acquired lock1");
        try {
          TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException ignore) {
        }
        synchronized (lock2) {
          LOGGER.info("Thread1 acquired lock2");
        }
      }
    }

  });

  /**
   *
   */
  private Thread thread2 = new Thread(new Runnable() {
    @Override
    public void run() {
      synchronized (lock2) {
        LOGGER.info("Thread2 acquired lock2");
        try {
          TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException ignore) {
        }
        synchronized (lock1) {
          LOGGER.info("Thread2 acquired lock1");
        }
      }
    }
  });

  /**
   *
   */
  public void startAndMonitorThreads() {
    try {
      threadLock.acquire();
      thread1.start();
      thread2.start();

      // monitor job will start after 2 seconds and run every 5 seconds.
      monitorService.scheduleAtFixedRate(new MonitorThread(), 2, monitorEvery, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.error("Interrupted while starting the Monitor thread.");
    } finally {
      threadLock.release();
    }

  }

  /**
   * @param bean
   * @param threadIds
   * @param info
   */
  private void logDeadlockAndQuit(ThreadMXBean bean, long[] threadIds, ThreadInfo[] info) {
    LOGGER.error("Threads in deadlocks: {}", Arrays.toString(threadIds));

    for (ThreadInfo threadInfo : info) {
      LOGGER.error("Thread \"{}\" is waiting on lock \"{}\" taken by thread \"{}\"",
        threadInfo.getThreadName(), threadInfo.getLockInfo(), threadInfo.getLockOwnerName());

      // Attempt to log the stack trace, when available
      for (StackTraceElement stackTraceElement : threadInfo.getStackTrace()) {
        LOGGER.error("{}::{} @ {}:{}",

          stackTraceElement.getClassName(), stackTraceElement.getMethodName(),
          stackTraceElement.getFileName(), stackTraceElement.getLineNumber());
      }
    }

  }

  /**
   *
   */
  private void stop() {
    try {
      LOGGER.info("Stopping threads.");
      if (thread1 != null) {
        LOGGER.info("Stopping thread1.");
        thread1.interrupt();
        thread1.join();
      }
      if (thread2 != null) {
        LOGGER.info("Stopping thread2.");
        thread2.interrupt();
        thread2.join();
      }
      monitorService.shutdownNow();
      monitorService.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.info("Interrupted while stopping");
    }
  }

  /**
   *
   */
  private class MonitorThread implements Runnable {
    @Override
    public void run() {
      try {
        threadLock.acquire();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] threadIds = bean.findDeadlockedThreads(); // Returns null if no threads are deadlocked
        if (threadIds != null) {
          ThreadInfo[] info = bean.getThreadInfo(threadIds);
          logDeadlockAndQuit(bean, threadIds, info);
          stop();
        } else {
          LOGGER.info("NO deadlocks");
        }
        threadLock.release();
      } catch (InterruptedException ie) {
        LOGGER.error("Monitor Thread got interrupted.", ie);
      } finally {
        threadLock.release();
      }
    }
  }

}
