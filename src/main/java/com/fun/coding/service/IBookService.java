package com.fun.coding.service;

import com.fun.coding.model.BookObject;
import com.fun.coding.exception.BookNotFoundException;
import com.fun.coding.model.Book;

/**
 * The Book service uses to create, obtain and delete book information
 * Declares methods used to find,create and delete book information.
 *
 * @author Nizar
 */
public interface IBookService {

  /**
   * Finds book by id.
   * @param isbn    The isbn of the wanted book.
   * @return  The found book. If no book is found, this method returns null.
   */
  Book findByIsbn(int isbn);

  /**
   * Deletes a book.
   * @param isbn  The isbn the deleted book.
   * @return  The deleted book.
   * @throws BookNotFoundException  if no book is found with the given isbn.
   */
  Book deleteBook(int isbn) throws BookNotFoundException;

  /**
   * Creates a new book.
   * @param created   The information of the created book.
   * @return  The created book.
   */
  Book createBook(BookObject created);

}
