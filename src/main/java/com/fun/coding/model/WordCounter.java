package com.fun.coding.model;

import java.util.Objects;

/**
 * Created by nizar on 1/12/18.
 * <p/>
 * This is the Word Counter Class
 * which has tow properties word and count.
 * It is used to maintain word count relationship.
 */
public class WordCounter implements Comparable<WordCounter> {

  private String word;

  private int count;

  public WordCounter(String word) {
    this.count = 1;
    this.word = word;
  }

  public void incrementCount() {
    this.count++;
  }

  public String getWord() {
    return word;
  }

  public int getCount() {
    return count;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WordCounter that = (WordCounter) o;
    return count == that.count &&
      Objects.equals(word, that.word);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, word);
  }

  @Override
  public String toString() {
    return "Words [word=" + word + ", count=" + count + "]";
  }

  /**
   * sorting words alphabetically
   *
   * @param other
   * @return
   */
  @Override
  public int compareTo(WordCounter other) {
    // sorting alphabetically

    return word.compareTo(other.getWord());

  }
}
