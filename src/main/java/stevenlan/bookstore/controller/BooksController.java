package stevenlan.bookstore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.dto.BooksRequest;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.entity.Books;
import stevenlan.bookstore.service.BooksService;
import stevenlan.bookstore.serviceImpl.BooksServiceImpl;
import stevenlan.bookstore.serviceImpl.EmployeesServiceImpl;




@RestController
@RequestMapping(path = "api/v1/books")

public class BooksController {

    private final BooksService booksService;
    
    private final EmployeesServiceImpl employeesServiceImpl;


    public BooksController(BooksServiceImpl booksService, EmployeesServiceImpl employeesServiceImpl) {
        this.booksService = booksService;
        this.employeesServiceImpl = employeesServiceImpl;
    }

    //改post會出問題
    @GetMapping
    public ResponseEntity<BooksResponse> getAllBooks(HttpServletRequest request){
        BooksRequest booksRequest = new BooksRequest(null, request, null);
        return ResponseEntity.ok(booksService.getAllBooks(booksRequest));
    }

    @PostMapping(value = {"/get/{bookIds}", "/get/"})
    public ResponseEntity<BooksResponse> getBooksByIds(@PathVariable(value = "bookIds", required = false) List<Long> booksId, HttpServletRequest request){
        //沒有作用
        if(booksId.isEmpty()||booksId == null){
            return ResponseEntity.badRequest().body(new BooksResponse(null, null, ""));
        }
        BooksRequest booksRequest = new BooksRequest(null, request, booksId);
        return ResponseEntity.ok(booksService.getBooksByIds(booksRequest));
    }

    @PostMapping
    public ResponseEntity<BooksResponse> addNewBooks(@RequestBody Books books, HttpServletRequest request){
        BooksRequest booksRequest = new BooksRequest(books, request,null);
        
        return ResponseEntity.ok(booksService.addNewBooks(booksRequest));
    }

    @DeleteMapping(path = "{booksIds}")
    public ResponseEntity<BooksResponse> deleteBooks(
        @PathVariable("booksIds") List<Long> bookIds, HttpServletRequest request){
            
        BooksRequest booksRequest = new BooksRequest(null, request,bookIds);

        return ResponseEntity.ok(booksService.deleteBooks(booksRequest));
    }

    @PutMapping(path = "{bookId}")
    public String updateBooks(
        @PathVariable("bookId") Long booksId,
        @RequestBody Books books, 
        HttpServletRequest request){
        booksService.updateBooks(booksId,books);    
            return employeesServiceImpl.tokenGenerate(request);
    }
}