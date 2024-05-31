package stevenlan.bookstore.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.entity.Books;

public interface BooksService {

    String getAllBooks(HttpServletRequest request);

    String getBooksByIds (List<Long> BookIds, HttpServletRequest request);

    String addNewBooks(Books books, HttpServletRequest request);

    String deleteBooks(List<Long> BookIds, HttpServletRequest request);

    void deleteBook(Long booksId);

    void updateBooks (Long booksId, Books newbooks);
}
