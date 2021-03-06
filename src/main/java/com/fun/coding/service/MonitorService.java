package com.fun.coding.service;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This implementation of the MonitorService interface.
 *
 * @author Nizar
 */
@Component
public class MonitorService implements IMonitorService {

  private static Logger LOGGER = LoggerFactory.getLogger(MonitorService.class);

  @Value("${monitor.service.exit}")
  private boolean isExit; // default value is true, disable this flag if you want to exits the service, in case deadlock is detected.

  @Value("${monitor.start.after}")
  private int monitorStartAfter; // run the monitor service after y seconds, configurable value.

  @Value("${monitor.run.every}")
  private int monitorEvery; // run the monitor service every x seconds, configurable value.

  @Value("${thread1.delay}")
  private int thread1Delay;

  @Value("${thread2.delay}")
  private int thread2Delay;


  private ScheduledExecutorService deadlockDetector = Executors.newScheduledThreadPool(1);

  private Object resource1 = new Object();

  private Object resource2 = new Object();


  private Semaphore threadLock = new Semaphore(1);

  //Thread1.
  //It tries to lock resource1 then resource2
  Thread thread1 = new Thread(new Runnable() {
    @Override
    public void run() {
      //This thread locks resource 2 right away
      synchronized (resource1) {
        LOGGER.info("Thread1 acquired resource1 delay time {} milliseconds",thread1Delay);
        try {
          //Pause for a bit
          TimeUnit.MILLISECONDS.sleep(thread1Delay);
        } catch (InterruptedException ignore) {
        }
        //Now wait till we can get a lock on resource 2
        synchronized (resource2) {
          LOGGER.info("Thread1 acquired resource2");
        }
      }
    }

  });

  //thread2.
  //It tries to lock resource2 then resource1
  Thread thread2 = new Thread(new Runnable() {
    @Override
    public void run() {
      //This thread locks resource 2 right away
      synchronized (resource2) {
        LOGGER.info("Thread2 acquired resource2 delay time {} milliseconds",thread2Delay);
        try{
          // it pauses for a bit
          Thread.sleep(thread2Delay);
        } catch (InterruptedException e){}

        //Then it tries to lock resource1.
        //Thread 1 locked resource1, and
        //won't release it till it gets a lock on resource2.
        //This thread holds the lock on resource2, and won't
        //release it till it gets resource1.
        //Neither thread can run,
        //and the program freezes up.
        synchronized(resource1){
          LOGGER.info("Thread 2: locked resource 1");
        }
      }
    }
  });

  /**
   * Start the two threads.
   * If all goes as planned, deadlock will occur,
   */
  public void start() {
    try {
      threadLock.acquire();
      thread1.start();
      thread2.start();

      // monitor job will start after x seconds and run every y seconds.
      LOGGER.info("scheduler start after {} and runs every  {} seconds",monitorStartAfter,monitorEvery);
      deadlockDetector.scheduleAtFixedRate(new DeadLockDetectorThread(), monitorStartAfter, monitorEvery, TimeUnit.SECONDS);
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
   * shutdown dead lock detector
   */
  public void stop() {
    try {
      LOGGER.info("shutdown the scheduler");
      deadlockDetector.shutdownNow();
      deadlockDetector.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOGGER.info("Interrupted while stopping");
    }
  }

  /**
   * Dead lock detector thread runs every 5 second
   * monitoring, and if it detects a deadlock problem
   * capture the details, add to the log and gracefully shutdown the service
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
          // We should trigger pager duty alert, to notify the ops folks
          LOGGER.info("Exit flag {}:",isExit);
          if(isExit) {
            LOGGER.info("Shutting Down the Service due to deadlock problem...!");
            System.exit(0);
          }
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
