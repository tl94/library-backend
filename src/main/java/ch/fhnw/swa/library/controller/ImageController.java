package ch.fhnw.swa.library.controller;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bson.types.ObjectId;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.fhnw.swa.library.entity.Book;
import ch.fhnw.swa.library.service.BookService;

@RestController
@RequestMapping("/images")
public class ImageController{
	
	private final Environment environment;
	
	private static final String IMAGES_DIR = "images.dir";
	
	private BookService bookService;
	
	public ImageController(BookService bookService, Environment environment) {
		this.bookService = bookService;
		this.environment = environment;
	}
	
	@PostMapping()
	public ResponseEntity<?> addImage(@RequestParam MultipartFile image, String bookId) throws IOException {
		try {
			Path uploadPath = Paths.get(environment.getProperty(IMAGES_DIR));
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			Book book = bookService.findBookById(bookId);
			
			if (book != null) {
				String fileName = book.getId() + "_" + (book.getImageRefs().size() + 1);
				bookService.addBookImage(book.getId(), fileName);
				Path filePath = uploadPath.resolve(fileName);
				image.transferTo(filePath.toFile());
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Failed to upload file.");
		}
	}
}
