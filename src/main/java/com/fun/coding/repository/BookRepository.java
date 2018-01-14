package com.fun.coding.repository;

import com.fun.coding.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nizar on 1/13/18.
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
}
