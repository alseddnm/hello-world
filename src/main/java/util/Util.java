package util;

/**
 * Created by nizar on 1/11/18.
 */
public class Util {
  /**
   * @param millis
   */
  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
