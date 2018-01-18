package com.fun.coding.service;

import com.fun.coding.model.BookObject;
import com.fun.coding.exception.BookNotFoundException;
import com.fun.coding.model.Book;
import com.fun.coding.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This implementation of the BookService interface communicates with
 * the database by using a Spring Data JPA repository.
 * @author Nizar
 */
@Component
public class BookService implements IBookService{

  private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

  @Autowired
  private BookRepository bookRepository;

  @Override
  public Book findByISBN(int isbn) {
    LOGGER.debug("Finding book by isbn: " + isbn);
    return bookRepository.findOne(isbn);
  }

  @Override
  public Book deleteBook(int isbn) {
    LOGGER.debug("Deleting book with isbn: " + isbn);

    Book deleted = bookRepository.findOne(isbn);

    if (deleted == null) {
      LOGGER.debug("No book found with isbn: " + isbn);
      throw new BookNotFoundException(isbn);
    }

    bookRepository.delete(deleted);
    return deleted;
  }

  @Override
  public Book createBook(BookObject bookObject) {
    LOGGER.debug("Creating a new book with isbn : " + bookObject.getIsbn());
    Book book = Book.getBuilder(bookObject.getTitle(), bookObject.getAuthor()).build();
    book.setIsbn(bookObject.getIsbn());
    return bookRepository.save(book);
  }

  /**
    * This setter method should be used only by unit tests.
    * @param bookRepository
  */
  protected void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

}
