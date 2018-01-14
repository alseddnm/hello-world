package com.fun.coding.app;

/**
 * Created by nizar on 1/10/18.
 */

import java.util.List;
import com.fun.coding.deadlock.DeadLocks;
import com.fun.coding.model.FibonacciSeries;
import com.fun.coding.model.Text;
import com.fun.coding.model.WordCounter;
import com.fun.coding.service.FibonacciService;
import com.fun.coding.service.WordsOccurrencesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestResources {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestResources.class);

  private static final String template = "Result= %s!";

  @RequestMapping("/healthcheck")
  public String healthCheck() {
    return "Ok...!";
  }


  @RequestMapping("/fibonacci/{number}")
  public ResponseEntity<FibonacciSeries> fib(@PathVariable(value = "number") int number) {
    List<Integer> fibonacciSeries = FibonacciService.buildFibonacciSeries(number);
    return new ResponseEntity<FibonacciSeries>(new FibonacciSeries(number, fibonacciSeries), HttpStatus.OK);
  }

  @RequestMapping(value = "/text", method = RequestMethod.POST)
  public ResponseEntity<List<WordCounter>> wordOccurrences(@RequestBody Text text) {
    List<WordCounter> list = WordsOccurrencesService.parseAndSortWordsAlphabetically(text.getContent());
    return new ResponseEntity<List<WordCounter>>(list, HttpStatus.OK);
  }

  @RequestMapping("/monitor/deadlock")
  public ResponseEntity<String> monitorDeadLocks() {
    DeadLocks.startThreads();
    String result = DeadLocks.monitorDeadlocks();
    return new ResponseEntity<String>(result, HttpStatus.OK);
  }


}
