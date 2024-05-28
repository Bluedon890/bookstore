package stevenlan.bookstore.books;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.employees.EmployeesService;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    private final EmployeesService employeesService;

    public String getAllBooks(HttpServletRequest request){
        List<Books> booksList = booksRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Books book : booksList) {
            bookStringBuilder(stringBuilder, book);
        }
        stringBuilder.append(employeesService.tokenGenerate(request));
        return stringBuilder.toString();
    }

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

    public String addNewBooks(Books books, HttpServletRequest request){
        Optional<Books> booksTitle = booksRepository.findBooksByTitle(books.getTitle());
        if(booksTitle.isPresent()){
            throw new IllegalStateException("this book is exist");
        }
        booksRepository.save(books);
        return employeesService.tokenGenerate(request);
    }

    public String deleteBooks(List<Long> BookIds, HttpServletRequest request){
        for(Long id : BookIds){
            deleteBook(id);
        }
        return employeesService.tokenGenerate(request);
    }

    public void deleteBook(Long booksId){
        boolean exists = booksRepository.existsById(booksId);
        if(!exists){
            throw new IllegalStateException("this id does not exists");
        }
        booksRepository.deleteById(booksId);
        
    }

    @Transactional
    public String updateBooks(
        Long booksId, String title, String author, String description, Integer listPrice, Integer salePrice, HttpServletRequest request){
            Books books = booksRepository.findById(booksId)
            .orElseThrow(() -> new IllegalStateException("this id does not exists"));

            if(title != null && title.length() > 0 && !Objects.equals(books.getTitle(), title)){
                books.setTitle(title);
            }
            if(author != null && author.length() > 0 && !Objects.equals(books.getAuthor(), author)){
                books.setAuthor(author);
            }
            if(description != null && description.length() > 0 && !Objects.equals(books.getDescription(), description)){
                books.setDescription(description);
            }
            if(listPrice != null && listPrice > 0 && !Objects.equals(books.getListPrice(), listPrice)){
                books.setListPrice(listPrice);
            }
            if(salePrice != null && salePrice > 0 && !Objects.equals(books.getSalePrice(), salePrice)){
                books.setSalePrice(salePrice);
            }
            return employeesService.tokenGenerate(request);
        }

}