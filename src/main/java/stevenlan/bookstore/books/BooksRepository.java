package stevenlan.bookstore.books;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long>{

     @Query("SELECT s FROM Books s WHERE s.title = ?1")
    Optional<Books> findBooksByTitle(String title);
}
