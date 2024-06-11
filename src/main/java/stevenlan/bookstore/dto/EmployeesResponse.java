package stevenlan.bookstore.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesResponse{

    private List<EmployeesDto> employeesDtos;

    private String message;

    private String jwtToken;
}
