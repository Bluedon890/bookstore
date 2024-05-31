package stevenlan.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.jwt.entity.AuthenticationResponse;
import stevenlan.bookstore.serviceImpl.EmployeesServiceImpl;

@RestController
@RequestMapping(path="api/v1/employees/admin")
public class AdminEmployeesController {

    private final EmployeesServiceImpl EmployeesServiceImpl;
    
    @Autowired
    public AdminEmployeesController(EmployeesServiceImpl EmployeesServiceImpl) {
        this.EmployeesServiceImpl = EmployeesServiceImpl;
    }

    @PostMapping(path = "{employeesId}")
	public ArrayList<String> getEmployees(
        @PathVariable("employeesId") List<Long> ids, HttpServletRequest request){
		return EmployeesServiceImpl.getEmployeesByIds(ids, request);
	}

    @DeleteMapping(path = "{employeesId}")
    public String deleteEmployees(
        @PathVariable("employeesId") List<Long> ids,HttpServletRequest request){
        return EmployeesServiceImpl.deleteEmployeesByIds(ids, request);
    }

    @PutMapping(path = "{employeesId}")
    public ResponseEntity<AuthenticationResponse> updateEmployees(
        @PathVariable("employeesId") Long employeesId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String account,
        @RequestParam(required = false) String password,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String phoneNumber,
        HttpServletRequest request){
            return ResponseEntity.ok(EmployeesServiceImpl.updateEmployee(employeesId,name,account,password,email,phoneNumber,request));

    }

}