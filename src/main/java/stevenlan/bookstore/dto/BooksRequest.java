package stevenlan.bookstore.dto;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

public class BooksRequest{

    private BooksDto books;

    private HttpServletRequest request;

    private List<Long> booksId;

    public BooksRequest(BooksDto books, HttpServletRequest request, List<Long> booksId) {
        this.books = books;
        this.request = request;
        this.booksId = booksId;
    }

    public BooksDto getBooks() {
        return books;
    }

    public void setBooks(BooksDto books) {
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
