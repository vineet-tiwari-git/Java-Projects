package com.vineet.bookService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vineet.bookService.model.Author;

public interface AuthorRepo extends JpaRepository<Author,Long> {

}
