package stevenlan.bookstore.service;

import jakarta.servlet.http.HttpServletRequest;
import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.dto.EmployeesUpdateRequest;
import stevenlan.bookstore.dto.EmployeesUserUpdateDto;
import stevenlan.bookstore.entity.Employees;

public interface EmlpoyeesService {

    public EmployeesResponse getEmployeesByIds(EmployeesIdsRequestDto EmployeesRequest);

    public EmployeesResponse deleteEmployeesByIds(EmployeesIdsRequestDto EmployeesRequest);

    public void deleteEmployeeById(Long EmployeesRequest);

    public EmployeesResponse updateEmployee(EmployeesUpdateRequest EmployeesRequest);

    public EmployeesResponse userUpdateEmployee(EmployeesUserUpdateDto EmployeesRequest);
    
    public Employees findEmployeeByRequest(HttpServletRequest request);
}
