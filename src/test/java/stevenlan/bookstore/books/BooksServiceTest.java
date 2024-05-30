package stevenlan.bookstore.books;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import stevenlan.bookstore.employees.EmployeesService;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private EmployeesService employeesService;

    @InjectMocks
    private BooksService booksService;

    private final Books book = Books.builder()
                .title("apple")
                .author("who")
                .description("apple is red")
                .listPrice(20)
                .salePrice(15).build();
    @Test
    public void BooksService_AddNewBooks_BookExists(){
        
        Books existingBook = new Books();
        existingBook.setTitle("Existing Book Title");
    
        when(booksRepository.findBooksByTitle(existingBook.getTitle())).thenReturn(Optional.of(existingBook));
        try {
            booksService.addNewBooks(existingBook, null);
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            assertEquals("this book is exist", e.getMessage());
        }

        // 確認沒有書籍被保存
        verify(booksRepository, never()).save(any(Books.class));
    }

    @Test
    public void BooksService_AddNewBooks_BookNotExists(){
        booksService.addNewBooks(book, null);
        verify(booksRepository, times(1)).save(book);
    }

    
}
