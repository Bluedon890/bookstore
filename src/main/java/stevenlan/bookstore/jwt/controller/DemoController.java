package stevenlan.bookstore.jwt.controller;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.BooksRequest;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.serviceImpl.EmployeesServiceImpl;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final EmployeesServiceImpl employeesServiceImpl;

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured url");
    }

    // @GetMapping("/test")
    // public ResponseEntity<BooksResponse> test(RequestEntity<BooksRequest> bookRequest) {
        
            
    // }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Hello from admin only url");
    }
}
