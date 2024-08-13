/**
 * 
 */
package ch.fhnw.swa.library.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ch.fhnw.swa.library.entity.Book;

/**
 * 
 */

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

	List<Book> findByTitle(String title);
	List<Book> findByAuthor(String author);
	List<Book> findByIsbn(String isbn);
}
