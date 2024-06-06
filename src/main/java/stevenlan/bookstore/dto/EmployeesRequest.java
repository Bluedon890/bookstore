package stevenlan.bookstore.dto;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.entity.Employees;

public class EmployeesRequest {

    private Employees employees;

    private List<Long> employeesId;

    private HttpServletRequest request;

    public EmployeesRequest(Employees employees, List<Long> employeesId, HttpServletRequest request) {
        this.employees = employees;
        this.employeesId = employeesId;
        this.request = request;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public List<Long> getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(List<Long> employeesId) {
        this.employeesId = employeesId;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
