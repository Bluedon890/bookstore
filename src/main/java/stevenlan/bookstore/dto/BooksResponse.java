package stevenlan.bookstore.dto;

import java.util.List;

public class BooksResponse {

    private String token;
    
    
    private List<BooksDto> books;

    private String message;

    public BooksResponse(String token, List<BooksDto> books, String message) {
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

    public List<BooksDto> getBooks() {
        return books;
    }

    public void setBooks(List<BooksDto> books) {
        this.books = books;
    }

}
