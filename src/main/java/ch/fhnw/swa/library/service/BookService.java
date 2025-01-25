package ch.fhnw.swa.library.service;

import java.util.List;
import java.util.Optional;

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

	public Book addBookImage(String bookId, String imageRef) {
		Book book = bookRepository.findById(bookId).get();
		book.addImageRef(imageRef);
		book = bookRepository.save(book);
		return book;
	}

//	will probably never be used
	public Book createBook(String title, String author, String isbn) {
		Book book = new Book(title, author, isbn);
		return bookRepository.save(book);
	}
	
	public Book updateBook(Book book) {
		Optional<Book> optOriginal = bookRepository.findById(book.getId());
		if (optOriginal.isPresent()) {
			Book original = optOriginal.get();
			original.setTitle(book.getTitle());
			original.setAuthor(book.getAuthor());
			original.setIsbn(book.getIsbn());
			return bookRepository.save(original);
		} else {
			return null;
		}
	}

	public Book deleteBook(String id) {
		Book book = findBookById(id);
		bookRepository.delete(book);
		return book;
	}

}
