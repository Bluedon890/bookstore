package stevenlan.bookstore.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesResponse{
    
    private String jwtToken;

    @JsonProperty("員工資料")
    private List<EmployeesDtoForResponse> employeesDtos;

    private String message;

    
}
