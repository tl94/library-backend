package ch.fhnw.swa.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.fhnw.swa.library.entity.Book;
import ch.fhnw.swa.library.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.listAllBooks();
	}

	@GetMapping("/{id}")
	public Book findBookById(@PathVariable String id) {
		return bookService.findBookById(id);
	}

	@PostMapping()
	public Book createBook(@RequestBody Book book) {
		return bookService.createBook(book);
	}

	@DeleteMapping("/{id}")
	public Book deleteBook(@PathVariable String id) {
		Book book = bookService.deleteBook(id);
		return book;
	}
}
