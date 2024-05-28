package stevenlan.bookstore.employees;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path="api/v1/employees")
public class EmployeesController {

    private final EmployeesService employeesService;
    
    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    
    @PostMapping(path = "{employeesId}")
	public ArrayList<String> getEmployees(
        @PathVariable("employeesId") List<Long> ids, HttpServletRequest request){
		return employeesService.getEmployeesByIds(ids, request);
		
	}
    @DeleteMapping()
    public void deleteEmployees(HttpServletRequest request){
        employeesService.deleteEmployeeById(
            employeesService.findEmployeeByRequest(request).getId());
    }

    @PutMapping()
    public void updateEmployees(
        @PathVariable("employeesId") Long employeesId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String account,
        @RequestParam(required = false) String password,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String phoneNumber,
        HttpServletRequest request){
            Long id = employeesService.findEmployeeByRequest(request).getId();
            employeesService.updateEmployee(id,name,account,password,email,phoneNumber,request);

    }

}

