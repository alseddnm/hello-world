package com.fun.coding.model;

/**
 * Created by nizar on 1/13/18.
 */

import javax.persistence.*;

/**
 * An entity class which contains the information of a single book.
 * @author Nizar Alseddeg
 */
@Entity
public class Book {

  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
  protected int isbn;

  protected String title;

  protected String author;

  public int getIsbn() {
    return isbn;
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

  /**
   * Gets a builder which is used to create Book objects.
   * @param title The title of the created title.
   * @param author  The author of the created author.
   * @return  A new Builder instance.
   */
  public static Builder getBuilder(String title, String author) {
    return new Builder(title, author);
  }

  /**
   * A Builder class used to create new Book objects.
   */
  public static class Builder {
    Book built;

    /**
     * Creates a new Builder instance.
     * @param title The title of the created Book object.
     * @param author  The author of the created Book object.
     */
    Builder(String title, String author) {
      built = new Book();
      built.title = title;
      built.author = author;
    }

    /**
     * Builds the new Book object.
     * @return  The created Book object.
     */
    public Book build() {
      return built;
    }
  }

  /**
   * This setter method should only be used by unit tests.
   * @param isbn
   */
  public void setIsbn(int isbn) {
    this.isbn = isbn;
  }

}
