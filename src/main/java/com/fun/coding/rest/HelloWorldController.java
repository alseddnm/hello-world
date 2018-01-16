package com.fun.coding.rest;

/**
 * Created by nizar on 1/10/18.
 */

import java.util.List;
import com.fun.coding.deadlock.MonitorService;
import com.fun.coding.model.FibonacciSeries;
import com.fun.coding.model.Text;
import com.fun.coding.model.WordCounter;
import com.fun.coding.service.FibonacciService;
import com.fun.coding.service.WordsOccurrencesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

  private static final String template = "Result= %s!";

  private final MonitorService monitorService;

  @Autowired
  public HelloWorldController(MonitorService monitorService) {
    this.monitorService = monitorService;
  }

  @RequestMapping("/")
  public String helloWorld() {
    return "Hello World!";
  }


  @RequestMapping("/fibonacci/{number}")
  public ResponseEntity<FibonacciSeries> fib(@PathVariable(value = "number") int number) {
    List<Integer> fibonacciSeries = FibonacciService.buildFibonacciSeries(number);
    return new ResponseEntity<>(new FibonacciSeries(number, fibonacciSeries), HttpStatus.OK);
  }

  @RequestMapping(value = "/words/count", method = RequestMethod.POST)
  public ResponseEntity<List<WordCounter>> wordOccurrences(@RequestBody Text text) {
    List<WordCounter> list = WordsOccurrencesService.parseAndSortWordsAlphabetically(text.getContent());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @RequestMapping("/monitor")
  public ResponseEntity<String> monitorDeadLocks() {
    monitorService.start();
    return new ResponseEntity<>("Monitoring...!", HttpStatus.OK);
  }


}
