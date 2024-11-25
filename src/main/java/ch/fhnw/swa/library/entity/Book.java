package ch.fhnw.swa.library.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Book {

	@Id
	private String id;

	private final String title;
	private final String author;
	private final String isbn;
	
	@DBRef
	private List<Image> images;

	public Book(String title, String author, String isbn) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}

	public String toString() {
		return String.format("Book[id=%s, title='%s', author='%s']", id, title, author);
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getIsbn() {
		return isbn;
	}

}
