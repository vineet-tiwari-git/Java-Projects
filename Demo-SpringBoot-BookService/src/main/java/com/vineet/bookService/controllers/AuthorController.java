package com.vineet.bookService.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vineet.bookService.model.Author;
import com.vineet.bookService.model.Book;
import com.vineet.bookService.service.AuthorService;

@RestController
@RequestMapping(path = "authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@GetMapping(path = "/{id}")
	private ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
		return this.authorService.getAuthorById(id).map(a -> ResponseEntity.ok(a))
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping
	private ResponseEntity<Iterable<Author>> getAuthors() {
		return ResponseEntity.ok(this.authorService.getAuthors());
	}

	@PostMapping(path = "/author")
	private ResponseEntity<Author> addAuthor(@RequestBody Author author) {
		return ResponseEntity.ok(this.authorService.addAuthor(author));
	}

	@PostMapping(path = "/{id}/addBook")
	private ResponseEntity<Author> addBookToAuthor(@PathVariable Long id, @RequestBody Book book) {
		Optional<Author> author = this.authorService.getAuthorById(id);
		return author.map(auth -> this.authorService.addBook(author.get(), book)).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		/*
		 * if(author.isPresent()) { Author updatedAuthor =
		 * this.authorService.addBook(author.get(), book); return
		 * ResponseEntity.ok(updatedAuthor); } else { return
		 * ResponseEntity.notFound().build(); }
		 */
	}

	@PostMapping
	private ResponseEntity<List<Author>> addAuthors(@RequestBody List<Author> authors) {
		return ResponseEntity.ok(this.authorService.addAuthors(authors));
	}

	@DeleteMapping
	private ResponseEntity deleteAllAuthors() {
		this.authorService.deleteAllAuthors();
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(path = "/{id}")
	private ResponseEntity deleteAuthorById(@PathVariable Long id) {
		this.authorService.deleteAuthorById(id);
		return ResponseEntity.noContent().build();
	}
};