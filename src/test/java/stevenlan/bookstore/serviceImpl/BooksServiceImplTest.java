package stevenlan.bookstore.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import stevenlan.bookstore.dto.BooksIdsRequest;
import stevenlan.bookstore.dto.BooksRequestDto;
import stevenlan.bookstore.entity.Books;
import stevenlan.bookstore.repository.BooksRepository;
import stevenlan.bookstore.service.BooksService;

public class BooksServiceImplTest {

    @Mock
    private BooksRepository booksRepository;
   

    private BooksService booksService;
    AutoCloseable autoCloseable;
    Books books;
    BooksRequestDto booksRequestDto;
    BooksIdsRequest booksIdsRequest;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        booksService = new BooksServiceImpl(booksRepository);
            
        books = new Books(1L,"apple", "anson", 
                "apple is red", 10, 20);

        booksRequestDto = new BooksRequestDto("apple", "anson", 
                "apple is red", 10, 20);

        booksIdsRequest = new BooksIdsRequest("1,2");
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    //新增成功
    @Test
    void testAddNewBooks_NoExistBook() {

        mock(BooksRepository.class);

        when(booksRepository.findByTitle("apple")).thenReturn(Optional.empty(),Optional.of(books));
        
        when(booksRepository.save(books)).thenReturn(books);
        
        assertThat(booksService.addNewBooks(booksRequestDto).getMessage()).isEqualTo("新增完成");
        assertThat(booksService.addNewBooks(booksRequestDto).getBooks().get(0).getTitle()).isEqualTo("apple");
        verify(booksRepository, times(1)).save(any(Books.class));
    }

    //新增失敗
    @Test
    void testAddNewBooks_AlreadyExistBook() {
        
        mock(BooksRepository.class);

        when(booksRepository.findByTitle("apple")).thenReturn(Optional.of(books));
        
        when(booksRepository.save(books)).thenReturn(books);
        
        assertThat(booksService.addNewBooks(booksRequestDto).getMessage()).isEqualTo("此書本已存在");
        verify(booksRepository, never()).save(any(Books.class));
    }

    //輸入1,2並成功刪除1
    @Test
    void testDeleteBooks_OnlyOneBookExist() {
        mock(BooksRepository.class);
        when(booksRepository.existsById(1L)).thenReturn(true);
        when(booksRepository.existsById(2L)).thenReturn(false);
        assertThat(booksService.deleteBooks(booksIdsRequest).getMessage()).isEqualTo("id為 2, 的資料不存在, 其餘刪除完畢");
        verify(booksRepository,times(1)).deleteById(any());
    }
    
    @Test
    void testGetBooksByIds() {

    }

    @Test
    void testUpdateBooks() {

    }
}
