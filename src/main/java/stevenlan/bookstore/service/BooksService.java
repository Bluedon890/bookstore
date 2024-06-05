package stevenlan.bookstore.service;

import stevenlan.bookstore.dto.BooksRequest;
import stevenlan.bookstore.dto.BooksResponse;

public interface BooksService {

    BooksResponse getAllBooks(BooksRequest booksRequest);

    BooksResponse getBooksByIds (BooksRequest booksRequest);

    BooksResponse addNewBooks(BooksRequest booksRequest);

    BooksResponse deleteBooks(BooksRequest booksRequest);

    BooksResponse updateBooks (BooksRequest booksRequest);
}
