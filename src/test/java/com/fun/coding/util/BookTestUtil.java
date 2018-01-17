package com.fun.coding.util;

import com.fun.coding.model.BookObject;
import com.fun.coding.model.Book;

/**
 * Created by nizar on 1/16/18.
 */
public class BookTestUtil {
  public static BookObject createBookObject(int isbn, String title, String author) {
    BookObject dto = new BookObject();

    dto.setIsbn(isbn);
    dto.setTitle(title);
    dto.setAuthor(author);

    return dto;
  }

  public static Book createModelObject(int isbn, String title, String author) {
    Book model = Book.getBuilder(title, author).build();

    model.setIsbn(isbn);

    return model;
  }
}
