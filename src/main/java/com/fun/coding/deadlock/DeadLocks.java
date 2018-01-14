package com.fun.coding.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nizar on 1/11/18.
 */
public class DeadLocks {
  private static Logger LOGGER = LoggerFactory.getLogger(DeadLocks.class);

  private static Semaphore threadLock = new Semaphore(1);

  public static void startThreads() {
    final Object lock1 = new Object();
    final Object lock2 = new Object();

    Thread thread1 = new Thread(new Runnable() {
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
    thread1.start();

    Thread thread2 = new Thread(new Runnable() {
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
    thread2.start();

    // Wait a little for threads to deadlock.
//    try {
//      TimeUnit.MILLISECONDS.sleep(100);
//    } catch (InterruptedException ignore) {}
//    detectDeadlock();
  }


  /**
   * @return
   */
  public static String monitorDeadlocks() {
    while (true) {
      try {
        threadLock.acquire();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] threadIds = bean.findDeadlockedThreads(); // Returns null if no threads are deadlocked
        if (threadIds != null) {
          ThreadInfo[] info = bean.getThreadInfo(threadIds);
          logDeadlockAndQuit(bean, threadIds, info);
          String message = String.format("Threads in deadlocks: {%s}", Arrays.toString(threadIds));
          return message;
        }
      } catch (InterruptedException e) {
        LOGGER.error("Interrupted while monitoring threads dead lock...!");
      } finally {
        threadLock.release();
      }
    }
  }

  /**
   * @param bean
   * @param threadIds
   * @param info
   */
  private static void logDeadlockAndQuit(ThreadMXBean bean, long[] threadIds, ThreadInfo[] info) {
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

//    System.exit(0);
  }

  /**
   * @param ms
   */
  private static void waitUninterruptedlyForMs(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      // Ignore it
    }
  }
}
