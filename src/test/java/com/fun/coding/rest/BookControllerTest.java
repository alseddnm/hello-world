package com.fun.coding.rest;

import com.fun.coding.Application;
import com.fun.coding.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
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

  @Test
  public void createRetrieveDeleteTest() throws Exception {
    Book book = new Book();
    book.setIsbn(123);
    book.setAuthor("author");
    book.setTitle("title");

    // create book
    mockMvc.perform(post("/books")
      .content(json(book))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(contentType))
      .andExpect(jsonPath("$.isbn", is(book.getIsbn())))
      .andExpect(jsonPath("$.title", is(book.getTitle())))
      .andExpect(jsonPath("$.author", is(book.getAuthor())));

    // retrieve book
    mockMvc.perform(get("/books/123")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(contentType))
      .andExpect(jsonPath("$.isbn", is(book.getIsbn())))
      .andExpect(jsonPath("$.title", is(book.getTitle())))
      .andExpect(jsonPath("$.author", is(book.getAuthor())));

    // delete book
    mockMvc.perform(delete("/books/123")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(contentType))
      .andExpect(jsonPath("$.isbn", is(book.getIsbn())));


    // delete book
    mockMvc.perform(delete("/books/123")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());

  }

}
