package stevenlan.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stevenlan.bookstore.entity.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

    Optional<Books> findByTitle(String title);
}
