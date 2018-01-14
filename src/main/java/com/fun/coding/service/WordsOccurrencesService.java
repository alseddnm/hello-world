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

//  /**
//   * You can use TreeMap as follow
//   * @param text
//   * @return
//   */
//  public static Map<String,Integer> wordCount(final String text) {
//    // check for empty or null
//    Map map = new TreeMap<>();  // word --> number of occurrences
//
//    StringBuffer word = new StringBuffer();
//
//    for(int i=0;i<text.length();i++){
//      if(text.charAt(i)!=SPACE) {
//        word.append(text.charAt(i));
//        if(i+1==text.length() || text.charAt(i+1)==SPACE) {
//          if(map.containsKey(word.toString())) {
//            // word exist in the map,
//            // increment its count by one
//            Integer count = (Integer)map.get(word.toString());
//            map.put(word.toString(), new Integer(count.intValue() + 1));
//          } else {
//            // Not seen this word.
//            map.put(word.toString(), new Integer(1));
//          }
//        }
//        continue;
//      }
//      word = new StringBuffer();
//    }
//
//    return map;
//
//  }

  /**
   * This method returns List of WordCounter sorted alphabetically.
   * If null/empty will return an empty list.
   *
   * @param text
   * @return
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
   * @param text
   * @param wordsMap
   */
  private static void addWordsToMap(String text, Map<String, WordCounter> wordsMap) {
    StringBuffer word = new StringBuffer();
    // Could use java regex to parse the text, why not?
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
