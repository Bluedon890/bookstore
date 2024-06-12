package stevenlan.bookstore.service;

import stevenlan.bookstore.dto.BooksIdsRequest;
import stevenlan.bookstore.dto.BooksRequestDto;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.dto.BooksUpdateRequestDto;

public interface BooksService {

    BooksResponse getBooksByIds(BooksIdsRequest booksRequest);

    BooksResponse addNewBooks(BooksRequestDto booksRequest);

    BooksResponse deleteBooks(BooksIdsRequest booksRequest);

    BooksResponse updateBooks(BooksUpdateRequestDto booksRequest);
}
