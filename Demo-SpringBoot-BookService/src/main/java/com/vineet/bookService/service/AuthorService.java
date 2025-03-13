package com.vineet.bookService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vineet.bookService.model.Author;
import com.vineet.bookService.model.Book;
import com.vineet.bookService.repo.AuthorRepo;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepo authorRepo;

	public Iterable<Author> getAuthors() {
		return this.authorRepo.findAll();
	}

	public Optional<Author> getAuthorById(Long id) {
		return this.authorRepo.findById(id);
	}

	public Author addAuthor(Author author) {
		return this.authorRepo.save(author);
	}

	public List<Author> addAuthors(List<Author> authors) {
		return this.authorRepo.saveAll(authors);
	}

	public void deleteAuthorById(Long id) {
		this.authorRepo.deleteById(id);
	}

	public void deleteAllAuthors() {
		this.authorRepo.deleteAll();
	}

	public Author addBook(Author author, Book book) {
		author.addBook(book);
		book.setAuthor(author);
		return this.authorRepo.save(author);
	}
}
