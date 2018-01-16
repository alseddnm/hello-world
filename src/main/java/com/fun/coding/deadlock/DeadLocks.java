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
import org.springframework.stereotype.Component;

/**
 * Created by nizar on 1/11/18.
 */
@Component
public class DeadLocks {

  private static Logger LOGGER = LoggerFactory.getLogger(DeadLocks.class);
  private final int monitorEvery = 60; // run the monitor service every 5 seconds. Should be configurable.
  private ScheduledExecutorService deadlockDetector = Executors.newScheduledThreadPool(1);
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
        System.out.println("Thread2 acquired lock2");
        synchronized (lock1) {
          System.out.println("Thread2 acquired lock1");
        }
      }
    }
  });

  /**
   *
   */
  public void start() {
    try {
      threadLock.acquire();
      thread1.start();
      thread2.start();

      // monitor job will start after 2 seconds and run every 5 seconds.
      deadlockDetector.scheduleAtFixedRate(new DeadLockDetectorThread(), 2, monitorEvery, TimeUnit.SECONDS);
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
    LOGGER.error("Threads are stuck in deadlock state: {}", Arrays.toString(threadIds));

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
  public void stop() {
    try {
      LOGGER.info("shutdown deadLock detector");
      deadlockDetector.shutdownNow();
      deadlockDetector.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.info("Interrupted while stopping");
    }
  }

  /**
   *
   */
  private class DeadLockDetectorThread implements Runnable {
    @Override
    public void run() {
      try {
        threadLock.acquire();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] threadIds = bean.findDeadlockedThreads();
        if (threadIds != null) {
          ThreadInfo[] info = bean.getThreadInfo(threadIds);
          logDeadlockAndQuit(bean, threadIds, info);
          LOGGER.info("Shutting Down the Service due to deadlock problem...!");
          System.exit(0);
        } else {
          LOGGER.info("NO deadlocks");
        }
      } catch (InterruptedException ie) {
        LOGGER.error("DeadLock Detector Thread got interrupted.", ie);
      } finally {
        threadLock.release();
      }
    }
  }
}
