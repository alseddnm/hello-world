package com.fun.coding.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nizar on 1/16/18.
 */
public class FibonacciServiceTest {

  /**
   * @throws Exception
   */
  @Test
  public void fibTest() throws Exception {
    // Mock the output data
    List<Integer> expected = new ArrayList<>();
    expected.add(0);
    expected.add(1);
    expected.add(1);
    expected.add(2);
    expected.add(3);

    List<Integer> result = FibonacciService.buildFibonacciSeries(5);

    Assert.assertEquals(expected,result);
  }
}
