package stevenlan.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.dto.EmployeesUpdateRequest;
import stevenlan.bookstore.jwt.entity.AuthenticationResponse;
import stevenlan.bookstore.serviceImpl.EmployeesServiceImpl;

@RestController
@RequestMapping(path = "api/v1/employees/pm")
public class AdminEmployeesController {

    private final EmployeesServiceImpl EmployeesServiceImpl;

    @Autowired
    public AdminEmployeesController(EmployeesServiceImpl EmployeesServiceImpl) {
        this.EmployeesServiceImpl = EmployeesServiceImpl;
    }

    @PostMapping(value = "/get")
    public ResponseEntity<EmployeesResponse> getEmployees(
            @RequestBody EmployeesIdsRequestDto ids) {
        return ResponseEntity.ok(EmployeesServiceImpl.getEmployeesByIds(ids));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<EmployeesResponse> deleteEmployees(
        @RequestBody EmployeesIdsRequestDto ids) {
        return ResponseEntity.ok(EmployeesServiceImpl.deleteEmployeesByIds(ids));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<EmployeesResponse> updateEmployees(
        @RequestBody EmployeesUpdateRequest req) {
        return ResponseEntity.ok(EmployeesServiceImpl.updateEmployee(req));

    }
    

}
