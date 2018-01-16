package com.fun.coding.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.fun.coding.model.WordCounter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nizar on 1/16/18.
 */
public class WordsOccurrencesServiceTest {

  /**
   * @throws Exception
   */
  @Test
  public void wordCountsTest() throws Exception {

    // Mock the output data
    List<WordCounter> expected = new ArrayList<>();
    WordCounter word1 = new WordCounter("and");
    WordCounter word2 = new WordCounter("coding");
    word2.incrementCount();
    WordCounter word3 = new WordCounter("fun");
    word3.incrementCount();
    WordCounter word4 = new WordCounter("is");
    word4.incrementCount();
    WordCounter word5 = new WordCounter("yes");

    expected.add(word1);
    expected.add(word2);
    expected.add(word3);
    expected.add(word4);
    expected.add(word5);
    Collections.sort(expected);

    final String inputText = "coding is fun and yes coding is fun";

    List<WordCounter> output = WordsOccurrencesService.parseAndSortWordsAlphabetically(inputText);
    Assert.assertEquals(expected,output);
  }

}

