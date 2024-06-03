package stevenlan.bookstore.serviceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
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
                    books, "資料查找完成");

    }

    @Override
    public BooksResponse getBooksByIds (BooksRequest booksRequest){
        try{
            List<Books> books = new ArrayList<>();
            for(Long id : booksRequest.getBooksId()){
                books.add(booksRepository.findById(id).orElseThrow(
                    ()->new RuntimeException("找不到此資料, 請勿輸入無效的id")));
        }
            return new BooksResponse(
                employeesService.tokenGenerate(booksRequest.getRequest()), 
                    books, "資料查找完成");
        }catch(RuntimeException e){
            return new BooksResponse(null, null, e.getMessage());
        }

    }

    @Override
    public String addNewBooks(Books books, HttpServletRequest request){
        Optional<Books> booksTitle = booksRepository.findBooksByTitle(books.getTitle());
        if(booksTitle.isPresent()){
            throw new IllegalStateException("this book is exist");
        }
        booksRepository.save(books);
        return employeesService.tokenGenerate(request);
    }

    @Override
    public String deleteBooks(List<Long> BookIds, HttpServletRequest request){
        for(Long id : BookIds){
            deleteBook(id);
        }   
        return employeesService.tokenGenerate(request);
    }

    @Override
    public void deleteBook(Long booksId){
        boolean exists = booksRepository.existsById(booksId);
        if(!exists){
            throw new IllegalStateException("this id does not exists");
        }
        booksRepository.deleteById(booksId);
        
    }

    @Override
    @Transactional
    public void updateBooks(
        Long booksId,
        Books newbooks){
        Books books = booksRepository.findById(booksId)
            .orElseThrow(() -> new IllegalStateException("this id does not exists"));

            if(newbooks.getTitle() != null && newbooks.getTitle().length() > 0 && !Objects.equals(books.getTitle(), newbooks.getTitle())){
                books.setTitle(newbooks.getTitle());
            }
            if(newbooks.getAuthor() != null && newbooks.getAuthor().length() > 0 && !Objects.equals(books.getAuthor(), newbooks.getAuthor())){
                books.setAuthor(newbooks.getAuthor());
            }
            if(newbooks.getDescription() != null && newbooks.getDescription().length() > 0 && !Objects.equals(books.getDescription(), newbooks.getDescription())){
                books.setDescription(newbooks.getDescription());
            }
            if(newbooks.getListPrice() != null && newbooks.getListPrice() > 0 && !Objects.equals(books.getListPrice(), newbooks.getListPrice())){
                books.setListPrice(newbooks.getListPrice());
            }
            if(newbooks.getSalePrice() != null && newbooks.getSalePrice() > 0 && !Objects.equals(books.getSalePrice(), newbooks.getSalePrice())){
                books.setSalePrice(newbooks.getSalePrice());
            }
            
        }
        

}