package stevenlan.bookstore.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.dto.BooksRequest;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.entity.Books;

public interface BooksService {

    BooksResponse getAllBooks(BooksRequest booksRequest);

    BooksResponse getBooksByIds (BooksRequest booksRequest);

    BooksResponse addNewBooks(BooksRequest booksRequest);

    BooksResponse deleteBooks(BooksRequest booksRequest);

    void updateBooks (Long booksId, Books newbooks);
}
