package stevenlan.bookstore.serviceImpl;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.entity.Books;
import stevenlan.bookstore.repository.BooksRepository;
import stevenlan.bookstore.service.BooksService;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService{

    private final BooksRepository booksRepository;

    private final EmployeesServiceImpl employeesService;

    @Override
    public String getAllBooks(HttpServletRequest request){
        List<Books> booksList = booksRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Books book : booksList) {
            bookStringBuilder(stringBuilder, book);
        }
        stringBuilder.append(employeesService.tokenGenerate(request));
        return stringBuilder.toString();

    }

    @Override
    public String getBooksByIds (List<Long> BookIds, HttpServletRequest request){
        StringBuilder stringBuilder = new StringBuilder();
        for(Long id : BookIds){
            bookStringBuilder(stringBuilder, booksRepository.findById(id).orElseThrow());
        }
        stringBuilder.append(employeesService.tokenGenerate(request));
        return stringBuilder.toString();
    }

    private void bookStringBuilder(StringBuilder stringBuilder, Books book) {
        stringBuilder.append("Id").append(book.getId()).append(",");
        stringBuilder.append("Title: ").append(book.getTitle()).append(", ");
        stringBuilder.append("Author: ").append(book.getAuthor()).append(", ");
        stringBuilder.append("Description: ").append(book.getDescription()).append(", ");
        stringBuilder.append("List Price: ").append(book.getListPrice()).append(", ");
        stringBuilder.append("Sale Price: ").append(book.getSalePrice()).append("\n");
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