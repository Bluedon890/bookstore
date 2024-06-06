package stevenlan.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.aspect.GetTokenAspect;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.service.BooksService;

@RestController
@RequestMapping(path = "api/v1/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    // 不輸入就是GetAll
    @PostMapping(value = "/get")
    public ResponseEntity<BooksResponse> getBooksByIds() {
        return GetTokenAspect.executeService(booksService::getBooksByIds);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<BooksResponse> addNewBooks() {
        return GetTokenAspect.executeService(booksService::addNewBooks);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<BooksResponse> deleteBooks() {
        return GetTokenAspect.executeService(booksService::deleteBooks);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<BooksResponse> updateBooks() {
        return GetTokenAspect.executeService(booksService::updateBooks);
    }
}