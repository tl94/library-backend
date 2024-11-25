package ch.fhnw.swa.library.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ch.fhnw.swa.library.entity.Image;

public interface ImageRepository extends MongoRepository<Image, ObjectId> {

}
