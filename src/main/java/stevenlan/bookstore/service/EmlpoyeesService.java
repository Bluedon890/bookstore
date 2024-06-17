package stevenlan.bookstore.service;

import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.dto.EmployeesUpdateRequest;
import stevenlan.bookstore.dto.EmployeesUserUpdateDto;

public interface EmlpoyeesService {

    public EmployeesResponse getEmployeesByIds(EmployeesIdsRequestDto EmployeesRequest);

    public EmployeesResponse deleteEmployeesByIds(EmployeesIdsRequestDto EmployeesRequest);

    public void deleteEmployeeById(Long EmployeesRequest);

    public EmployeesResponse updateEmployee(EmployeesUpdateRequest EmployeesRequest);

    public EmployeesResponse userUpdateEmployee(EmployeesUserUpdateDto EmployeesRequest);
    
    
}
