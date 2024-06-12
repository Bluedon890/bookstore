package stevenlan.bookstore.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import stevenlan.bookstore.entity.Role;

@Data
@NoArgsConstructor
public class EmployeesRequestDto {

    private String account;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private List<Role> roles;

}