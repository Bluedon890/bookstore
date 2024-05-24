package stevenlan.bookstore.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping(path = "api/v1/books")
public class BooksController {

    private final BooksService booksService;
    
    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<Books> getAllBooks(){
        return booksService.getAllBooks();
    }

    @PostMapping
    public void addNewBooks(@RequestBody Books books){
        booksService.addNewBooks(books);
    }

    @DeleteMapping(path = "{booksId}")
    public void deleteBooks(
        @PathVariable("booksId") Long booksId){
            booksService.deleteBooks(booksId);
    }

    @PutMapping(path = "{booksId}")
    public void updateBooks(
        @PathVariable("booksId") Long booksId,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) Integer listPrice,
        @RequestParam(required = false) Integer salePrice){
            booksService.updateBooks(booksId,title,author,description,listPrice,salePrice);
    }
}