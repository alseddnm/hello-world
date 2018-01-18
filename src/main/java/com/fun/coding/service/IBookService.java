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
  *
  * @param isbn  The isbn of the wanted book.
  * @return a {@link Book}
  */
  Book findByISBN(int isbn);

  /**
   * Deletes a book.
   * Return the deleted book.
   *
   * @param isbn  The isbn the deleted book.
   * @return  a {@link Book}
   * @throws {@link BookNotFoundException}  if no book is found with the given isbn.
   */
  Book deleteBook(int isbn) throws BookNotFoundException;

  /**
   * Creates a new book.
   * @param created   The information of the created book.
   * @return  a {@link Book}
   */
  Book createBook(BookObject created);

}
