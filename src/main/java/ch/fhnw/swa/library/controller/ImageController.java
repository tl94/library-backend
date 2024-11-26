package ch.fhnw.swa.library.controller;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.fhnw.swa.library.entity.Book;
import ch.fhnw.swa.library.entity.Image;
import ch.fhnw.swa.library.service.BookService;
import ch.fhnw.swa.library.service.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController{

	private ImageService imageService;
	
	private BookService bookService;
	
	public ImageController(ImageService imageService, BookService bookService) {
		this.imageService = imageService;
		this.bookService = bookService;
	}
	
	@PostMapping
	public ObjectId addImage(@RequestParam MultipartFile image, String bookId) throws IOException {
		Image newImage = imageService.addImage(image);
		Book book = bookService.addBookImage(bookId, newImage);
		return newImage.getId();
	}
	
	@GetMapping("/{id}")
	public Image getImage(@PathVariable ObjectId id) {
		Image image = imageService.getImage(id);
		return image;
	}
}
