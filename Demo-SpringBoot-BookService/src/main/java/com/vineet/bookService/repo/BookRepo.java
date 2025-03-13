package com.vineet.bookService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vineet.bookService.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

}
