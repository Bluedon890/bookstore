package stevenlan.bookstore.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.entity.Books;

public class BooksRequest {

    private Books books;
    
    private HttpServletRequest request;

    private List<Long> booksId;

    

    public BooksRequest(Books books, HttpServletRequest request, List<Long> booksId) {
        this.books = books;
        this.request = request;
        this.booksId = booksId;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public List<Long> getBooksId() {
        return booksId;
    }

    public void setBooksId(List<Long> booksId) {
        this.booksId = booksId;
    }

}
