package com.vineet.bookService.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vineet.bookService.dto.BookDTO;
import com.vineet.bookService.model.Book;
import com.vineet.bookService.repo.BookRepo;

@Service
public class BookService {

	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private ModelMapper mapper;

	public Iterable<BookDTO> getBooks() {
		return this.bookRepo.findAll().stream().map(a -> mapper.map(a, BookDTO.class)).collect(Collectors.toList());

	}

	public Optional<BookDTO> getBookById(Long id) {
		return this.bookRepo.findById(id).map(a -> mapper.map(a, BookDTO.class));
	}

	public Book addBook(Book book) {
		return this.bookRepo.save(book);
	}

	public List<Book> addBooks(List<Book> books) {
		return this.bookRepo.saveAll(books);
	}

	public void deleteBookById(Long id) {
		this.bookRepo.deleteById(id);
	}

	public void deleteAllBook() {
		this.bookRepo.deleteAll();
	}
}