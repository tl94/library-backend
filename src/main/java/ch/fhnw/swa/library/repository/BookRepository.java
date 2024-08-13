/**
 * 
 */
package ch.fhnw.swa.library.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ch.fhnw.swa.library.entity.Book;

/**
 * 
 */

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

	List<Book> findByTitle(@Param("title") String title);

	List<Book> findByAuthor(@Param("title") String author);

	List<Book> findByIsbn(@Param("title") String isbn);
}
