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
import org.springframework.web.bind.annotation.RequestParam;
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

    //不輸入就是GetAll
    @PostMapping(value = "/get")
    public ResponseEntity<BooksResponse> getBooksByIds(
        @RequestParam(value = "bookIds", required = false) List<Long> booksId, 
        HttpServletRequest request){

        BooksRequest booksRequest = new BooksRequest(
            null, request, booksId);
        if(booksId.isEmpty()||booksId == null){
            return ResponseEntity.ok(booksService.getAllBooks(booksRequest));
        }
        return ResponseEntity.ok(booksService.getBooksByIds(booksRequest));
    }

    @PostMapping
    public ResponseEntity<BooksResponse> addNewBooks(
        @RequestBody Books books, HttpServletRequest request){
        BooksRequest booksRequest = new BooksRequest(
            books, request,null);
        
        return ResponseEntity.ok(booksService.addNewBooks(booksRequest));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<BooksResponse> deleteBooks(
        @RequestParam(value = "bookIds", required = false) List<Long> bookIds, 
        HttpServletRequest request){
        BooksRequest booksRequest = new BooksRequest(null, request, bookIds);

        return ResponseEntity.ok(booksService.deleteBooks(booksRequest));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<BooksResponse> updateBooks(
        @RequestParam(value = "bookId", required = false) List<Long> bookId,
        @RequestBody Books books, 
        HttpServletRequest request){
            BooksRequest booksRequest = new BooksRequest(books, request, bookId);
            return ResponseEntity.ok(booksService.updateBooks(booksRequest));
    }
}