package stevenlan.bookstore.jwt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("message")
    private String message;

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

    public AuthenticationResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

}
