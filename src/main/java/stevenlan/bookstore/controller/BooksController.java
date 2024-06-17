package stevenlan.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.BooksIdsRequest;
import stevenlan.bookstore.dto.BooksRequestDto;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.dto.BooksUpdateRequestDto;
import stevenlan.bookstore.service.BooksService;

@RestController
@RequestMapping(path = "api/v1/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    // 不輸入就是GetAll
    @PostMapping(value = "/get")
    public ResponseEntity<BooksResponse> getBooksByIds(@RequestBody BooksIdsRequest ids) {
        return ResponseEntity.ok(booksService.getBooksByIds(ids));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<BooksResponse> addNewBooks(@RequestBody @Valid BooksRequestDto req) {
        return ResponseEntity.ok(booksService.addNewBooks(req));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<BooksResponse> deleteBooks(@RequestBody BooksIdsRequest req) {
        return ResponseEntity.ok(booksService.deleteBooks(req));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<BooksResponse> updateBooks(@RequestBody BooksUpdateRequestDto req) {
        return ResponseEntity.ok(booksService.updateBooks(req));
    }
}