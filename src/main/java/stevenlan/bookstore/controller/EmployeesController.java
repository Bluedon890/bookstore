package stevenlan.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.dto.EmployeesUserUpdateDto;
import stevenlan.bookstore.service.EmlpoyeesService;


@RestController
@RequestMapping(path = "api/v1/employees")
@RequiredArgsConstructor
public class EmployeesController {

    private final EmlpoyeesService employeesService;

    // 不輸入就是GetAll
    @PostMapping(value = "/get")
    public ResponseEntity<EmployeesResponse> getEmployees(
            @RequestBody EmployeesIdsRequestDto ids) {
        return ResponseEntity.ok(employeesService.getEmployeesByIds(ids));
    }

    @DeleteMapping(value = "/delete")
    public String deleteEmployees(HttpServletRequest request) {
        employeesService.deleteEmployeeById(
                employeesService.findEmployeeByRequest(request).getId());
        return ("您的帳號已刪除");
    }

    @PutMapping(value = "/update")
    public ResponseEntity<EmployeesResponse> updateEmployees(@RequestBody EmployeesUserUpdateDto req) {

        return ResponseEntity.ok(employeesService.userUpdateEmployee(req));

    }

}
