package ch.fhnw.swa.library.entity;

import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Image {
	@Id
	private ObjectId id;
	
	private Binary image;
}
