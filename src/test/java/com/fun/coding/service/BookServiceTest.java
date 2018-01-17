package com.fun.coding.service;

import com.fun.coding.model.BookObject;
import com.fun.coding.exception.BookNotFoundException;
import com.fun.coding.model.Book;
import com.fun.coding.repository.BookRepository;
import com.fun.coding.util.BookTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;


import static org.mockito.Mockito.*;
import static junit.framework.Assert.assertEquals;

/**
 * Created by nizar on 1/16/18.
 */
public class BookServiceTest {

  private static final int ISBN = 123;
  private static final String TITLE = "title";
  private static final String AUTHOR = "author";

  private BookService bookService;

  private BookRepository bookRepositoryMock;

  @Before
  public void setUp() {
    bookService = new BookService();

    bookRepositoryMock = mock(BookRepository.class);
    bookService.setBookRepository(bookRepositoryMock);
  }

  @Test
  public void createBookTest() {
    BookObject created = BookTestUtil.createBookDTO(0, TITLE, AUTHOR);
    Book persisted = BookTestUtil.createModelObject(ISBN, TITLE, AUTHOR);

    when(bookRepositoryMock.save(any(Book.class))).thenReturn(persisted);

    Book returned = bookService.createBook(created);

    ArgumentCaptor<Book> bookArgument = ArgumentCaptor.forClass(Book.class);
    verify(bookRepositoryMock, times(1)).save(bookArgument.capture());
    verifyNoMoreInteractions(bookRepositoryMock);

    assertBook(created, bookArgument.getValue());
    assertEquals(persisted, returned);
  }


  @Test
  public void findById() {
    Book person = BookTestUtil.createModelObject(ISBN, TITLE, AUTHOR);
    when(bookRepositoryMock.findOne(ISBN)).thenReturn(person);

    Book returned = bookService.findByIsbn(ISBN);

    verify(bookRepositoryMock, times(1)).findOne(ISBN);
    verifyNoMoreInteractions(bookRepositoryMock);

    assertEquals(person, returned);
  }

  @Test
  public void deleteBookTest() throws BookNotFoundException {
    Book deleted = BookTestUtil.createModelObject(ISBN, TITLE, AUTHOR);
    when(bookRepositoryMock.findOne(ISBN)).thenReturn(deleted);

    Book returned = bookService.deleteBook(ISBN);

    verify(bookRepositoryMock, times(1)).findOne(ISBN);
    verify(bookRepositoryMock, times(1)).delete(deleted);
    verifyNoMoreInteractions(bookRepositoryMock);

    assertEquals(deleted, returned);
  }


  private void assertBook(BookObject expected, Book actual) {
    assertEquals(expected.getIsbn(), actual.getIsbn());
    assertEquals(expected.getTitle(), actual.getTitle());
    assertEquals(expected.getAuthor(), expected.getAuthor());
  }
}
