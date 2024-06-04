package stevenlan.bookstore.serviceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.BooksRequest;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.entity.Books;
import stevenlan.bookstore.repository.BooksRepository;
import stevenlan.bookstore.service.BooksService;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService{

    private final BooksRepository booksRepository;

    private final EmployeesServiceImpl employeesService;

    @Override
    public BooksResponse getAllBooks(BooksRequest booksRequest){
        List<Books> books = booksRepository.findAll();
        return new BooksResponse(
                employeesService.tokenGenerate(booksRequest.getRequest()), 
                    books, "資料查詢完畢");

    }

    
    @Override
    public BooksResponse getBooksByIds (BooksRequest booksRequest){
        List<Books> books = new ArrayList<>();
        String nonExistId = "";
        for(Long id : booksRequest.getBooksId()){
            if(booksRepository.findById(id).isPresent()){
                books.add(booksRepository.findById(id).orElseThrow());
            }else{
                nonExistId += id;
                nonExistId +=", ";
            }
        }
            if(!nonExistId.isBlank()){
                return new BooksResponse(
                employeesService.tokenGenerate(booksRequest.getRequest()), 
                books, "查詢完畢, id為 " + nonExistId + "的資料不存在");
            }else{   
                return new BooksResponse(
                    employeesService.tokenGenerate(booksRequest.getRequest()), 
                books, "查詢完畢");
            }   
    }

    @Override
    public BooksResponse addNewBooks(BooksRequest booksRequest){
        if(booksRepository.findBooksByTitle(booksRequest.getBooks().getTitle()).isPresent()){
            return new BooksResponse(null, 
                Arrays.asList(booksRequest.getBooks()), "此書本已存在");
        }
        booksRepository.save(booksRequest.getBooks());
        return new BooksResponse(
            employeesService.tokenGenerate(booksRequest.getRequest()), 
            Arrays.asList(booksRequest.getBooks()), 
            "新增完成");
    }

    @Override
    public BooksResponse deleteBooks(BooksRequest booksRequest){
        if(booksRequest.getBooksId().isEmpty()){
            return new BooksResponse(null, null, "請輸入欲刪除的書本id");
        }
        String nonExistId = "";
        for(Long id : booksRequest.getBooksId()){
            if(booksRepository.existsById(id)){
                booksRepository.deleteById(id);
            }else{
                nonExistId += id;
                nonExistId +=", ";
            }
        }
        if(!nonExistId.isBlank()){
            return new BooksResponse(
            employeesService.tokenGenerate(booksRequest.getRequest()), 
            null, "刪除完畢, id為 " + nonExistId + "的資料不存在");
        }else{   
            return new BooksResponse(
                employeesService.tokenGenerate(booksRequest.getRequest()), 
            null, "刪除完畢");
        }
    }

    @Override
    @Transactional
    public BooksResponse updateBooks(BooksRequest booksRequest){
        if(booksRequest.getBooksId().isEmpty()){
            return new BooksResponse(null, null, "請輸入欲更新的書本id");
        }
        Books book = booksRepository.findById(booksRequest.getBooksId().get(0)).orElse(null);
        if(book == null){
            return new BooksResponse(
                employeesService.tokenGenerate(booksRequest.getRequest()), 
            null, "更新失敗, 此id之資料不存在");
        }else{
            Books newBook = booksRequest.getBooks();
            if(newBook.getTitle() != null && newBook.getTitle().length() > 0 && !Objects.equals(book.getTitle(), newBook.getTitle())){
                book.setTitle(newBook.getTitle());
            }
            if(newBook.getAuthor() != null && newBook.getAuthor().length() > 0 && !Objects.equals(book.getAuthor(), newBook.getAuthor())){
                book.setAuthor(newBook.getAuthor());
            }
            if(newBook.getDescription() != null && newBook.getDescription().length() > 0 && !Objects.equals(book.getDescription(), newBook.getDescription())){
                book.setDescription(newBook.getDescription());
            }
            if(newBook.getListPrice() != null && newBook.getListPrice() > 0 && !Objects.equals(book.getListPrice(), newBook.getListPrice())){
                book.setListPrice(newBook.getListPrice());
            }
            if(newBook.getSalePrice() != null && newBook.getSalePrice() > 0 && !Objects.equals(book.getSalePrice(), newBook.getSalePrice())){
                book.setSalePrice(newBook.getSalePrice());
            }
            return new BooksResponse(
                employeesService.tokenGenerate(booksRequest.getRequest()), 
            null, "更新完畢");
        }
    }
}