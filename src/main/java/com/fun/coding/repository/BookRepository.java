package com.fun.coding.repository;

import com.fun.coding.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Specifies methods used to obtain and modify book related information
 * which is stored in the database.
 * @author Nizar
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
}
