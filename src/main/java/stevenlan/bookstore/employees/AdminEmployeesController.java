package stevenlan.bookstore.employees;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path="api/v1/employees/admin")
public class AdminEmployeesController {

    private final EmployeesService employeesService;
    
    @Autowired
    public AdminEmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @PostMapping(path = "{employeesId}")
	public ArrayList<String> getEmployees(
        @PathVariable("employeesId") List<Long> ids){
		return employeesService.getEmployeesById(ids);
		
	}

    @DeleteMapping(path = "{employeesId}")
    public String deleteEmployees(
        @PathVariable("employeesId") List<Long> ids,HttpServletRequest request){
        return employeesService.deleteEmployees(ids, request);
    }

    @PutMapping(path = "{employeesId}")
    public String updateEmployees(
        @PathVariable("employeesId") Long employeesId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String account,
        @RequestParam(required = false) String password,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String phoneNumber,
        HttpServletRequest request){
            return employeesService.updateEmployees(employeesId,name,account,password,email,phoneNumber,request);

    }

}
