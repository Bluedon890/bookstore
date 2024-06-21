package stevenlan.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesRegisterRequest {

    private String account;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;
}
