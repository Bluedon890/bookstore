package stevenlan.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(path = "api/v1/employees/pm")
@RequiredArgsConstructor
public class AdminEmployeesController {

    private final EmlpoyeesService employeesService;

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
