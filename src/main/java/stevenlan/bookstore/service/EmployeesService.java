package stevenlan.bookstore.service;

import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.dto.EmployeesUpdateRequest;

public interface EmployeesService {

    public EmployeesResponse getEmployeesByIds(EmployeesIdsRequestDto EmployeesRequest);

    public EmployeesResponse deleteEmployeesByIds(EmployeesIdsRequestDto EmployeesRequest);

    public EmployeesResponse updateEmployee(EmployeesUpdateRequest EmployeesRequest);
}
