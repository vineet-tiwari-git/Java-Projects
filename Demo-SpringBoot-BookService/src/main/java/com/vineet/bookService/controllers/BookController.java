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

import com.vineet.bookService.dto.BookDTO;
import com.vineet.bookService.model.Book;
import com.vineet.bookService.service.BookService;

@RestController
@RequestMapping(path = "books")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	private ResponseEntity<Iterable<BookDTO>> getAllBooks() {
		return ResponseEntity.ok(this.bookService.getBooks());
	}

	@GetMapping("/{id}")
	private ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
		return this.bookService.getBookById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/book")
	private ResponseEntity<Book> createBook(@RequestBody Book book) {
		return ResponseEntity.ok(this.bookService.addBook(book));
	}

	@PostMapping()
	private ResponseEntity<List<Book>> createBooks(@RequestBody List<Book> books) {
		return ResponseEntity.ok(this.bookService.addBooks(books));
	}

	@DeleteMapping
	private ResponseEntity deleteBooks() {
		this.bookService.deleteAllBook();
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	private ResponseEntity deleteBookById(@PathVariable Long id) {
		this.bookService.deleteBookById(id);
		return ResponseEntity.noContent().build();
	}
}
