package stevenlan.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BooksRequest {

    @JsonProperty("token")
    private String token;

    @JsonProperty("message")
    private String message;
}
