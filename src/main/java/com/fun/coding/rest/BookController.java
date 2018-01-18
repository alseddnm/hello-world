package com.fun.coding.rest;


import com.fun.coding.exception.BookNotFoundException;
import com.fun.coding.model.Book;
import com.fun.coding.model.BookObject;
import com.fun.coding.service.BookService;
import com.fun.coding.service.IBookService;
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

/**
 * Created by nizar on 1/13/18.
 */
@RestController
@RequestMapping("/books")
public class BookController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

  @Autowired
  private IBookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  /**
   * Get book requests.
   * @param isbn  The isbn of the book.
   * @return  The Book.
   */
  @RequestMapping(method = RequestMethod.GET, value = "/{isbn}")
  public ResponseEntity<Book> findBook(@PathVariable int isbn) {
    LOGGER.debug("REST request to retrieve Book : {}", isbn);
    Book book = bookService.findByISBN(isbn);
    if (book == null) {
      throw new BookNotFoundException(isbn);
    }
    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  /**
   * Create book requests.
   * @param bookObject
   * @return  The created book.
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> addBook(@RequestBody BookObject bookObject) {
    LOGGER.debug("REST request to create Book : {}", bookObject.getIsbn());
    Book result = bookService.createBook(bookObject);
    if (result == null) {
      return ResponseEntity.noContent().build();
    }
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  /**
   * Processes delete book requests.
   * @param isbn  The isbn of the deleted book.
   * @return
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/{isbn}")
  public ResponseEntity<?> deleteBook(@PathVariable int isbn) {
    LOGGER.debug("REST request to delete Book : {}", isbn);
    final String result = "{\"isbn\": %d}";
    try {
      bookService.deleteBook(isbn);
      return new ResponseEntity<>(String.format(result,isbn), HttpStatus.OK);
    } catch (BookNotFoundException e) {
      LOGGER.info("No book found with isbn {$d} .", isbn);
      return ResponseEntity.noContent().build();
    }
  }

}
