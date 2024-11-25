package ch.fhnw.swa.library.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.swa.library.entity.Image;

@Repository
public interface ImageRepository extends MongoRepository<Image, ObjectId> {

}
