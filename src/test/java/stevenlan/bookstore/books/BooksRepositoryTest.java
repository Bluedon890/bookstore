package stevenlan.bookstore.books;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;




@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BooksRepositoryTest {

    @Autowired
    private BooksRepository booksRepository;

    @Test
    public void BooksRepository_Save_ReturnSavedBook(){

        //Arrange
        Books book = Books.builder()
                .title("apple")
                .author("who")
                .description("apple is red")
                .listPrice(20)
                .salePrice(15).build();

        //Act
        Books savedBook = booksRepository.save(book);

        // //Assert
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isGreaterThan(0);
    }   

    
}
