package ch.fhnw.swa.library.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Book {

	@Id
	private String id;

	private String title;
	private String author;
	private String isbn;
	
	@DBRef
	private List<String> imageRefs;

	public Book(String title, String author, String isbn) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<String> getImageRefs() {
		return imageRefs;
	}

	public void setImageRefs(List<String> imageRefs) {
		this.imageRefs = imageRefs;
	}

	public String getId() {
		return id;
	}
	
	public void addImageRef(String imageRef) {
		imageRefs.add(imageRef);
	}
	
	public String toString() {
		return String.format("Book[id=%s, title='%s', author='%s']", id, title, author);
	}
}
