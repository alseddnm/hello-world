package com.fun.coding.model;

import java.util.List;

/**
 * Created by nizar on 1/12/18.
 */
public class FibonacciSeries {

  private int number;

  private List<Integer> SeriesList;

  public FibonacciSeries(int number, List<Integer> seriesList) {
    this.number = number;
    SeriesList = seriesList;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public List<Integer> getSeriesList() {
    return SeriesList;
  }

  public void setSeriesList(List<Integer> seriesList) {
    SeriesList = seriesList;
  }

}
