package ch.fhnw.swa.library.controller;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.fhnw.swa.library.entity.Image;
import ch.fhnw.swa.library.service.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController{

	private ImageService imageService;
	
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@GetMapping
	public ObjectId addImage(@RequestParam("image") MultipartFile image) throws IOException {
		ObjectId id = imageService.addImage(image);
		return id;
	}
	
	@GetMapping("/{id}")
	public Image getImage(@PathVariable ObjectId id) {
		Image image = imageService.getImage(id);
		return image;
	}
}
