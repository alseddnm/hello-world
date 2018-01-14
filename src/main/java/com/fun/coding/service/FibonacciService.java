package com.fun.coding.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nizar on 1/12/18.
 */
public class FibonacciService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FibonacciService.class);

  public FibonacciService() {

  }

  /**
   * @param n
   * @return
   */
  public static List<Integer> buildFibonacciSeries(int n) {
    int firstIndex = 0, secondIndex = 1;
    LOGGER.info("Building Fibonacci Series ...!");
    List<Integer> series = new ArrayList<>();

    if (n < 0) return series; // empty list;

    if (n == 0) {
      series.add(firstIndex);
      return series;
    }
    series.add(firstIndex); // add 0
    series.add(secondIndex); // add 1
    calculateFibonacci(firstIndex, secondIndex, n - 2, series); //number-2 because 2 numbers are already printed
    return series;
  }

  /**
   * @param
   * @return
   */
  private static void calculateFibonacci(int firstIndex, int secondIndex, int count, List<Integer> series) {
    if (count > 0) {
      int number = firstIndex + secondIndex;
      firstIndex = secondIndex;
      secondIndex = number;
      series.add(number);
      calculateFibonacci(firstIndex, secondIndex, count - 1, series);
    }
  }

}
//https://www.javatpoint.com/fibonacci-series-in-java