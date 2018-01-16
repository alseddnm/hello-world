package com.fun.coding.rest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.fun.coding.Application;
import com.fun.coding.model.FibonacciSeries;
import com.fun.coding.model.Text;
import com.fun.coding.model.WordCounter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class HelloWorldControllerTest {

  @Autowired
  private MockMvc mvc;

  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(),
    Charset.forName("utf8"));

  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {

    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
      .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
      .findAny()
      .orElse(null);

    assertNotNull("the JSON message converter must not be null",
      this.mappingJackson2HttpMessageConverter);
  }
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
    // Mock the output data
    List<Integer> list = new ArrayList<>();
    list.add(0);
    list.add(1);
    list.add(1);
    list.add(2);
    list.add(3);
    FibonacciSeries fibonacciSeries = new FibonacciSeries(5,list);

    mvc.perform(get("/fibonacci/5").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.number", is((int) fibonacciSeries.getNumber())))
      .andExpect(jsonPath("$.seriesList[0]", is(fibonacciSeries.getSeriesList().get(0))))
      .andExpect(jsonPath("$.seriesList[1]", is(fibonacciSeries.getSeriesList().get(1))))
      .andExpect(jsonPath("$.seriesList[2]", is(fibonacciSeries.getSeriesList().get(2))))
      .andExpect(jsonPath("$.seriesList[3]", is(fibonacciSeries.getSeriesList().get(3))))
      .andExpect(jsonPath("$.seriesList[4]", is(fibonacciSeries.getSeriesList().get(4))));
  }

  /**
   * @throws Exception
   */
  @Test
  public void wordCountsTest() throws Exception {

    // Mock the output data
    List<WordCounter> list = new ArrayList<>();
    WordCounter word1 = new WordCounter("and");
    WordCounter word2 = new WordCounter("coding");
    word2.incrementCount();
    WordCounter word3 = new WordCounter("fun");
    word3.incrementCount();
    WordCounter word4 = new WordCounter("is");
    word4.incrementCount();
    WordCounter word5 = new WordCounter("yes");

    list.add(word1);
    list.add(word2);
    list.add(word3);
    list.add(word4);
    list.add(word5);
    Collections.sort(list);
    // text data , input text
    Text text =  new Text();
    text.setContent("coding is fun and yes coding is fun");

    mvc.perform(post("/text")
      .content(this.json(text))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(contentType))
      .andExpect(jsonPath("$", hasSize(5)))
      .andExpect(jsonPath("$[0].word", is(list.get(0).getWord())))
      .andExpect(jsonPath("$[0].count", is(list.get(0).getCount())))
      .andExpect(jsonPath("$[1].word", is(list.get(1).getWord())))
      .andExpect(jsonPath("$[1].count", is(list.get(1).getCount())))
      .andExpect(jsonPath("$[2].word", is(list.get(2).getWord())))
      .andExpect(jsonPath("$[2].count", is(list.get(2).getCount())))
      .andExpect(jsonPath("$[3].word", is(list.get(3).getWord())))
      .andExpect(jsonPath("$[3].count", is(list.get(3).getCount())))
      .andExpect(jsonPath("$[4].word", is(list.get(4).getWord())))
      .andExpect(jsonPath("$[4].count", is(list.get(4).getCount())));

  }


  private String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(
      o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }
}
