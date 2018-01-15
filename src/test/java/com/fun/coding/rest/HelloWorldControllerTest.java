package com.fun.coding.rest;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fun.coding.Application;
import com.fun.coding.model.FibonacciSeries;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class HelloWorldControllerTest {

  @Autowired
  private MockMvc mvc;

  /**
   * @throws Exception
   */
  @Test
  public void helloWorldTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().string(equalTo("Hello World!")));
  }

  /**
   * @throws Exception
   */
  @Test
  public void fibTest() throws Exception {
    List<Integer> list = new ArrayList<>();
    list.add(0);
    list.add(1);
    list.add(1);
    list.add(2);
    list.add(3);
    FibonacciSeries fibonacciSeries = new FibonacciSeries(5,list);
//    byte[] fibJson = toJson(list);
    mvc.perform(MockMvcRequestBuilders.get("/fibonacci/5").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.number", is((int) fibonacciSeries.getNumber())))
      .andExpect(jsonPath("$.seriesList[0]", is(fibonacciSeries.getSeriesList().get(0))))
      .andExpect(jsonPath("$.seriesList[1]", is(fibonacciSeries.getSeriesList().get(1))))
      .andExpect(jsonPath("$.seriesList[2]", is(fibonacciSeries.getSeriesList().get(2))))
      .andExpect(jsonPath("$.seriesList[3]", is(fibonacciSeries.getSeriesList().get(3))))
      .andExpect(jsonPath("$.seriesList[4]", is(fibonacciSeries.getSeriesList().get(4))));


  }

  private byte[] toJson(Object r) throws Exception {
    ObjectMapper map = new ObjectMapper();
    return map.writeValueAsString(r).getBytes();
  }


}

// https://stackoverflow.com/questions/39084491/unable-to-find-a-springbootconfiguration-when-doing-a-jpatest
// https://spring.io/guides/gs/spring-boot/

//https://www.leveluplunch.com/java/tutorials/014-post-json-to-spring-rest-webservice/