package stevenlan.bookstore.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.entity.Books;

public interface BooksServise {

    public String getAllBooks(HttpServletRequest request);

    public String getBooksByIds (List<Long> BookIds, HttpServletRequest request);

    public String addNewBooks(Books books, HttpServletRequest request);

    public String deleteBooks(List<Long> BookIds, HttpServletRequest request);

    public void deleteBook(Long booksId);

    public String updateBooksLong (Long booksId, Books newbooks, HttpServletRequest request);
}
