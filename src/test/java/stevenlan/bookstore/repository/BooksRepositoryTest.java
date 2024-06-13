package stevenlan.bookstore.repository;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import stevenlan.bookstore.entity.Books;

@DataJpaTest
public class BooksRepositoryTest {

    @Autowired
    private BooksRepository booksRepository;
    Books books;
    
    @BeforeEach
    void setUp() {
        books = new Books(1L,"apple", "anson", "apple is red", 10, 20);
        booksRepository.save(books);
    }

    @AfterEach
    void tearDown() {
        books = null;
        booksRepository.deleteAll();
    }

    //成功找到
    @Test
    void testFindBooksByTitle_Found(){
        Optional<Books> foundBooks= booksRepository.findBooksByTitle("apple");
        assertThat(foundBooks.get().getAuthor()).isEqualTo(books.getAuthor());
        assertThat(foundBooks.get().getId()).isEqualTo(books.getId());
    }
    //失敗
    @Test
    void testFindBooksByTitle_NotFound(){
        Optional<Books> foundBooks= booksRepository.findBooksByTitle("banana");
        assertThat(foundBooks.isEmpty()).isTrue();
    }
}

