package stevenlan.bookstore.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import stevenlan.bookstore.dto.BooksIdsRequest;
import stevenlan.bookstore.dto.BooksRequestDto;
import stevenlan.bookstore.dto.BooksUpdateRequestDto;
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
    BooksUpdateRequestDto booksUpdateRequestDto;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        booksService = new BooksServiceImpl(booksRepository);
            
        books = new Books(1L,"apple", "anson", 
                "apple is red", 10, 20);

        booksRequestDto = new BooksRequestDto("apple", "anson", 
                "apple is red", 10, 20);

        booksIdsRequest = new BooksIdsRequest("1,2,3,4");
    
        booksUpdateRequestDto = new BooksUpdateRequestDto(1L, "apple", "anson", 
                "apple is red", 10, 20);
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
        mock(BooksRepository.class, Mockito.CALLS_REAL_METHODS);
        doAnswer(Answers.CALLS_REAL_METHODS).when(booksRepository).deleteById(any());
        when(booksRepository.existsById(1L)).thenReturn(true);
        when(booksRepository.existsById(2L)).thenReturn(false);
        
        assertThat(booksService.deleteBooks(booksIdsRequest).getMessage()).isEqualTo("id為 2, 3, 4的資料不存在, 其餘刪除完畢");
        verify(booksRepository,times(1)).deleteById(any());
    }

    @Test
    void testDeleteBooks_NoId() {
        BooksIdsRequest req = new BooksIdsRequest("");
        assertThat(booksService.deleteBooks(req).getMessage()).isEqualTo("請輸入欲刪除的書本id");
        verify(booksRepository,never()).deleteById(any());
    }
    
    @Test
    void testGetBooksByIds_GetOne() {
        mock(BooksRepository.class);
        when(booksRepository.findById(1L)).thenReturn(Optional.of(books));
        when(booksRepository.findById(2L)).thenReturn(Optional.empty());
        assertThat(booksService.getBooksByIds(booksIdsRequest).getMessage()).isEqualTo("查詢完畢, id為 2, 3, 4的資料不存在");
        assertThat(booksService.getBooksByIds(booksIdsRequest).getBooks().get(0).getTitle()).isEqualTo(books.getTitle());
    }

    @Test
    void testGetBooksByIds_GetAll() {
        mock(BooksRepository.class);
        when(booksRepository.findAll()).thenReturn(java.util.Arrays.asList(books));
        BooksIdsRequest req = new BooksIdsRequest("");
        assertThat(booksService.getBooksByIds(req).getMessage()).isEqualTo("查詢完畢");
        assertThat(booksService.getBooksByIds(req).getBooks().get(0).getTitle()).isEqualTo(books.getTitle());
    }

    @Test
    void testUpdateBooks_Success() {
        mock(BooksRepository.class);
        mock(Books.class);
        when(booksRepository.findById(1L)).thenReturn(Optional.of(books));
        assertThat(booksService.updateBooks(booksUpdateRequestDto).getMessage()).isEqualTo("更新完畢");
        assertThat(booksService.updateBooks(booksUpdateRequestDto).getBooks().get(0).getTitle()).isEqualTo(booksUpdateRequestDto.getTitle());
    }

    @Test
    void testUpdateBooks_NoId() {
        booksUpdateRequestDto.setBookId(null);
        assertThat(booksService.updateBooks(booksUpdateRequestDto).getMessage()).isEqualTo("請輸入欲更新的書本id");
    }

    @Test
    void testUpdateBooks_NoData() {
        mock(BooksRepository.class);
        when(booksRepository.findById(2L)).thenReturn(Optional.empty());
        booksUpdateRequestDto.setBookId(2L);
        assertThat(booksService.updateBooks(booksUpdateRequestDto).getMessage()).isEqualTo("更新失敗, 此id之資料不存在");
    }
}
