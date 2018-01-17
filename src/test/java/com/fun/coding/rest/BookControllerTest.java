package com.fun.coding.rest;

import com.fun.coding.Application;
import com.fun.coding.model.BookObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by nizar on 1/15/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class BookControllerTest extends BaseRestTest{

  private static final Logger LOGGER = LoggerFactory.getLogger(BookControllerTest.class);

  @Before
  public void setup() {
  }

  @Test
  public void createRetrieveDeleteTest() throws Exception {
    BookObject bookObject = new BookObject();
    bookObject.setIsbn(1);
    bookObject.setTitle("title");
    bookObject.setAuthor("author");
    // create book
    LOGGER.info("Create,Get and Delete REST APIs Test");
    mockMvc.perform(post("/books")
      .content(json(bookObject))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(contentType))
      .andExpect(jsonPath("$.isbn", is(is(bookObject.getIsbn()))))
      .andExpect(jsonPath("$.title", is(bookObject.getTitle())))
      .andExpect(jsonPath("$.author", is(bookObject.getAuthor())));

    // retrieve book
    mockMvc.perform(get("/books/1")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(contentType))
      .andExpect(jsonPath("$.isbn", is(bookObject.getIsbn())))
      .andExpect(jsonPath("$.title", is(bookObject.getTitle())))
      .andExpect(jsonPath("$.author", is(bookObject.getAuthor())));

    // delete book
    mockMvc.perform(delete("/books/1")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(contentType))
      .andExpect(jsonPath("$.isbn", is(bookObject.getIsbn())));

  }

}
