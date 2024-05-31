package stevenlan.bookstore.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.jwt.entity.AuthenticationResponse;
import stevenlan.bookstore.jwt.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/v1/adminregister")
    public ResponseEntity<AuthenticationResponse> adminregister(
            @RequestBody Employees request){
        
        return ResponseEntity.ok(authService.adminRegister(request));
    }
    @PostMapping("/v1/employeesregister")
    public ResponseEntity<AuthenticationResponse> employeesregister(
            @RequestBody Employees request){
        
        return ResponseEntity.ok(authService.employeesRegister(request));
    }

    @PostMapping("/v1/login")
    public ResponseEntity<AuthenticationResponse>login(
            @RequestBody Employees request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    
}
