package stevenlan.bookstore.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesResponse{
    
    private String jwtToken;

    private List<EmployeesDtoForResponse> employeesDtos;

    private String message;
    
}
