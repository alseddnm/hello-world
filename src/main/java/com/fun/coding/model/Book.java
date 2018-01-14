package com.fun.coding.model;

/**
 * Created by nizar on 1/13/18.
 */

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

  @Id
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

}
