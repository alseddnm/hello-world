package com.fun.coding.service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import com.fun.coding.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static junit.framework.Assert.assertEquals;

/**
 * Created by nizar on 1/17/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MonitorServiceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(MonitorServiceTest.class);

  @Autowired
  private MonitorService monitorService;

  /**
   * Wrote this test to verify deadlock is detected.
   * @throws InterruptedException
   */
  @Test
  public void monitorTest() throws InterruptedException {
    monitorService.start();
    // run scheduler every5 seconds
    Thread.sleep(1000); // pause for one second
    ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    long[] threadIds = bean.findDeadlockedThreads();
    LOGGER.info(" Before: We have {} threads in deadlock state", threadIds.length);
    assertEquals(threadIds.length,2);
    Thread.sleep(2000); // pause for 2 seconds, for the deadlock detector thread to start -> then exit
    // Although system.exit(0) got executed I do still see java process is running, I had to do execute kill -9
    // if you run this test , do (ps -ef | grep java)
    // -> jstack (PID) if you see deadlock issue -> execute -> kill -9 (PID)
  }
}
