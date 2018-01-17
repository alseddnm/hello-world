package com.fun.coding.model;

import javax.persistence.Id;

/**
 * A Book object which is used as a form object
 * in create book.
 * @author Nizar
 */
public class BookObject {

  protected int isbn;

  protected String title;

  protected String author;

  public int getIsbn() {
    return isbn;
  }

  public void setIsbn(int isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public String toString() {
    return "{" +
      "isbn=" + isbn +
      ", title='" + title + '\'' +
      ", author='" + author + '\'' +
      '}';
  }
}
