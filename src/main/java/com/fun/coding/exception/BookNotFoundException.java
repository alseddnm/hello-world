package com.fun.coding.exception;

/**
 * Created by nizar on 1/13/18.
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(int bookId) {
    super("could not find book '" + bookId + "'.");
  }
}
