package com.fun.coding.service;

/**
 * The Monitor service uses to creates two threads that become deadlocked with each other,
 * created a scheduler runs every 5 seconds to monitor the jvm for deadlock
 * Shutdown the service once the deadlock is detected
 * Declares methods used to start threads and stop.
 *
 * @author Nizar
 */
public interface IMonitorService {
  void start();
  void stop();
}
