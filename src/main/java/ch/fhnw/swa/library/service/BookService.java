package ch.fhnw.swa.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.fhnw.swa.library.entity.Book;
import ch.fhnw.swa.library.repository.BookRepository;

@Service
public class BookService {

	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> listAllBooks() {
		return bookRepository.findAll();
	}

	public Book findBookById(String id) {
		return bookRepository.findById(id).orElse(null);
	}

	public List<Book> findByTitle(String title) {
		return bookRepository.findByTitle(title);
	}

	public List<Book> findByAuthor(String author) {
		return bookRepository.findByAuthor(author);
	}

	public List<Book> findByIsbn(String isbn) {
		return bookRepository.findByIsbn(isbn);
	}

	public Book createBook(Book book) {
		return bookRepository.save(book);
	}

//	will probably never be used
	public Book createBook(String title, String author, String isbn) {
		Book book = new Book(title, author, isbn);
		return bookRepository.save(book);
	}

	public Book deleteBook(String id) {
		Book book = findBookById(id);
		bookRepository.delete(book);
		return book;
	}

}
