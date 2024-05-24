package stevenlan.bookstore.books;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Books> getAllBooks(){
        return booksRepository.findAll();
    }

    public void addNewBooks(Books books){
        Optional<Books> booksTitle = booksRepository.findBooksByTitle(books.getTitle());
        if(booksTitle.isPresent()){
            throw new IllegalStateException("this book is exist");
        }
        booksRepository.save(books);
    }

    public void deleteBooks(Long booksId){
        boolean exists = booksRepository.existsById(booksId);
        if(!exists){
            throw new IllegalStateException("this id does not exists");
        }
        booksRepository.deleteById(booksId);
    }

    @Transactional
    public void updateBooks(
        Long booksId, String title, String author, String description, Integer listPrice, Integer salePrice){
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
            
        }

}