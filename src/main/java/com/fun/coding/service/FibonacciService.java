package com.fun.coding.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Fibonacci Service
 * The class has one method, which accepts a number N  and returns a JSON array with the first N Fibonacci numbers.
 * @author Nizar
 */
public class FibonacciService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FibonacciService.class);

  public FibonacciService() {

  }

  /**
   * Returns a list contains the first N Fibonacci numbers.
   *
   * @param number the Fibonacci number
   * @return a {@link List} with a response type of {@link Integer}
   */
  public static List<Integer> buildFibonacciSeries(int number) {
    int firstIndex = 0, secondIndex = 1;
    LOGGER.info("Building Fibonacci Series ...!");
    List<Integer> series = new ArrayList<>();

    if (number < 0) return series; // empty list;

    if (number == 0) {
      series.add(firstIndex);
      return series;
    }
    series.add(firstIndex); // add 0
    series.add(secondIndex); // add 1
    calculateFibonacci(firstIndex, secondIndex, number - 2, series); //number-2 because 2 numbers are already printed
    return series;
  }

  /**
   *
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
