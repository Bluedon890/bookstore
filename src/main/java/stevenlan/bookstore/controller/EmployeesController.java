package stevenlan.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.dto.EmployeesUpdateRequest;
import stevenlan.bookstore.service.EmlpoyeesService;


@RestController
@RequestMapping(path = "api/v1/employees")
@RequiredArgsConstructor
public class EmployeesController {

    private final EmlpoyeesService employeesService;

    @PostMapping(value = "/get")
    public ResponseEntity<EmployeesResponse> getEmployees(
            @RequestBody EmployeesIdsRequestDto ids) {
        return ResponseEntity.ok(employeesService.getEmployeesByIds(ids));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<EmployeesResponse> deleteEmployees(
            @RequestBody EmployeesIdsRequestDto ids) {
        return ResponseEntity.ok(employeesService.deleteEmployeesByIds(ids));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<EmployeesResponse> updateEmployees(
            @RequestBody EmployeesUpdateRequest req) {
        return ResponseEntity.ok(employeesService.updateEmployee(req));

    }

}
