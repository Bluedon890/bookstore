package stevenlan.bookstore.controller;

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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.entity.Books;
import stevenlan.bookstore.serviceImpl.BooksServiceImpl;
import stevenlan.bookstore.serviceImpl.EmployeesServiceImpl;




@RestController
@RequestMapping(path = "api/v1/books")

public class BooksController {

    private final BooksServiceImpl booksService;
    
    private final EmployeesServiceImpl employeesServiceImpl;


    public BooksController(BooksServiceImpl booksService, EmployeesServiceImpl employeesServiceImpl) {
        this.booksService = booksService;
        this.employeesServiceImpl = employeesServiceImpl;
    }

    //改post會出問題
    @GetMapping
    public String getAllBooks(HttpServletRequest request){
        return booksService.getAllBooks(request);
    }

    @PostMapping(path = "{bookIds}")
    public String getBooksByIds(@PathVariable("bookIds") List<Long> booksId, HttpServletRequest request){
        return booksService.getBooksByIds(booksId, request);
    }

    @PostMapping
    public String addNewBooks(@RequestBody Books books, HttpServletRequest request){
        return booksService.addNewBooks(books,request);
    }

    @DeleteMapping(path = "{booksIds}")
    public String deleteBooks(
        @PathVariable("booksIds") List<Long> bookIds, HttpServletRequest request){
            return booksService.deleteBooks(bookIds,request);
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