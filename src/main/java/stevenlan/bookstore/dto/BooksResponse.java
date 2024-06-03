package stevenlan.bookstore.dto;

import java.util.List;

import stevenlan.bookstore.entity.Books;

public class BooksResponse {

    private String token;

    private List<Books> books;

    private String message;

    

    public BooksResponse(String token, List<Books> books, String message) {
        this.token = token;
        this.books = books;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    
}
