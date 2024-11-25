package ch.fhnw.swa.library.service;

import java.io.IOException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.fhnw.swa.library.entity.Image;
import ch.fhnw.swa.library.repository.ImageRepository;

@Service
public class ImageService {

	private final ImageRepository imageRepository;

	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	public ObjectId addImage(MultipartFile file) throws IOException {
		Image image = new Image();
		image.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		image = imageRepository.save(image);
		return image.getId();
	}
	
	public Image getImage(ObjectId id) {
		return imageRepository.findById(id).get();
	}
}
