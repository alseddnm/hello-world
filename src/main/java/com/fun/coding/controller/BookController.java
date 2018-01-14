package com.fun.coding.controller;


import com.fun.coding.exception.BookNotFoundException;
import com.fun.coding.model.Book;
import com.fun.coding.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nizar on 1/13/18.
 */
@RestController
@RequestMapping("/book")
public class BookController {

  private static final Logger logger = LoggerFactory.getLogger(BookController.class);

  private final BookRepository bookmarkRepository;

  @Autowired
  public BookController(BookRepository bookmarkRepository) {
    this.bookmarkRepository = bookmarkRepository;
  }

  @RequestMapping("/healthcheck")
  public String healthCheck() {
    return "Ok...!";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
  public ResponseEntity<Book> findBook(@PathVariable int bookId) {
    Book book = bookmarkRepository.findOne(bookId);
    if (book == null) {
      throw new BookNotFoundException(bookId);
    }
    return new ResponseEntity<Book>(book, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> addBook(@RequestBody Book book) {
    Book result = bookmarkRepository.save(book);
    if (result == null) {
      return ResponseEntity.noContent().build();
    }
    return new ResponseEntity<Book>(result, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/{bookId}")
  public ResponseEntity<?> deleteBook(@PathVariable int bookId) {
    try {
      bookmarkRepository.delete(bookId);
    } catch (EmptyResultDataAccessException e) {
      throw new BookNotFoundException(bookId);
    }

    Book book = bookmarkRepository.findOne(bookId);
    if (book == null) {
      return new ResponseEntity<String>("deleted book with ID = '" + bookId + "'.", HttpStatus.OK);

    }
    throw new BookNotFoundException(bookId);
  }

// https://stackoverflow.com/questions/29808051/spring-boot-annotation-autowired-of-service-fails
  // https://spring.io/guides/tutorials/bookmarks/
}