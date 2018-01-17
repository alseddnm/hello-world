package com.fun.coding.rest;

/**
 * Created by nizar on 1/13/18.
 */

import java.util.List;
import com.fun.coding.model.FibonacciSeries;
import com.fun.coding.model.Text;
import com.fun.coding.model.WordCounter;
import com.fun.coding.service.FibonacciService;
import com.fun.coding.service.IMonitorService;
import com.fun.coding.service.MonitorService;
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

  private final IMonitorService monitorService;

  @Autowired
  public HelloWorldController(MonitorService monitorService) {
    this.monitorService = monitorService;
  }

  @RequestMapping("/")
  public String helloWorld() {
    return "Hello World!";
  }

  /**
   * GET /fibonacci/[number].
   *
   * @param number  accepts an integer a number
   * @return  ResponseEntity<FibonacciSeries> returns a JSON array with the first N Fibonacci numbers.
   */
  @RequestMapping("/fibonacci/{number}")
  public ResponseEntity<FibonacciSeries> fib(@PathVariable(value = "number") int number) {
    LOGGER.debug("Rest call to create fibonacci numbers.");
    List<Integer> fibonacciSeries = FibonacciService.buildFibonacciSeries(number);
    if(fibonacciSeries==null || fibonacciSeries.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return new ResponseEntity<>(new FibonacciSeries(number, fibonacciSeries), HttpStatus.OK);
  }

  /**
   * POST /words/occurrences.
   *
   * @param text  Accepts a JSON object containing a paragraph of text.
   * @return  ResponseEntity<List<WordCounter>> returns a JSON array of objects,
   *          represent the unique words from the supplied paragraph along with a count of the number of occurrences
   */
  @RequestMapping(value = "/words/occurrences", method = RequestMethod.POST)
  public ResponseEntity<List<WordCounter>> wordOccurrences(@RequestBody Text text) {
    LOGGER.debug("Rest API to return words and number occurrences sorted alphabetically.");
    List<WordCounter> list = WordsOccurrencesService.parseAndSortWordsAlphabetically(text.getContent());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * GET /monitor.
   * Creates two threads that become deadlocked with each other.
   * Use the api to monitor the two threads and detect the deadlock,
   * logging the details and gracefully shutdown the service, in case a deadlock occurred
   *
   * @return  ResponseEntity<String>
   */
  @RequestMapping("/monitor")
  public ResponseEntity<String> monitorDeadLocks() {
    LOGGER.debug("Rest API to monitor and detect deadlock.");
    monitorService.start();
    return new ResponseEntity<>("Monitoring...!", HttpStatus.OK);
  }


}
