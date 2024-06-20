package stevenlan.bookstore.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stevenlan.bookstore.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesUpdateRequest {

    @NotNull(message = "請填要更改的id!")
    private Long employeesId;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private List<Role> roles;
}
