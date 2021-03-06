package com.fun.coding.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fun.coding.model.WordCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static java.util.Collections.sort;

/**
 * Created by nizar on 1/12/18.
 */
public class WordsOccurrencesService {

  private static final Logger LOGGER = LoggerFactory.getLogger(WordsOccurrencesService.class);

  private static final char SPACE = ' ';

  /**
   * This method returns List of WordCounter sorted alphabetically.
   * If null/empty will return an empty list.
   *
   * @param text
   * @return a {@link List} with a response type of {@link WordCounter}
   */
  public static List<WordCounter> parseAndSortWordsAlphabetically(String text) {
    if (null == text || text.trim().isEmpty()) return new ArrayList<>(); // return an empty list.

    LOGGER.info("Prase and sort words alphabetically...!");
    Map<String, WordCounter> wordsMap = new HashMap<String, WordCounter>();  // word --> number of occurrences

    addWordsToMap(text, wordsMap);

    // Create a list of entries and sort the list
    List<WordCounter> list = new ArrayList<>();
    for (WordCounter wordCounter : wordsMap.values()) {
      list.add(wordCounter);
    }
    sort(list);
    return list;
  }

  /**
   * This method count the number of occurrences if each word in a text
   *
   * The code assumes spaces are the only words delimiter
   * @param text the input String, the text paragraph
   * @param wordsMap The Map<String,WordCounter>
   */
  private static void addWordsToMap(String text, Map<String, WordCounter> wordsMap) {
    StringBuffer word = new StringBuffer();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) != SPACE) {
        word.append(text.charAt(i));
        if (i + 1 == text.length() || text.charAt(i + 1) == SPACE) {
          WordCounter wordCounter = wordsMap.get(word.toString());
          if (wordCounter == null) {
            // Not seen this word.
            wordsMap.put(word.toString(), new WordCounter(word.toString()));
          } else {
            // word exist in the map,
            // increment its count by one
            wordCounter.incrementCount();
          }
        }
        continue;
      }
      word = new StringBuffer();
    }
  }

}
