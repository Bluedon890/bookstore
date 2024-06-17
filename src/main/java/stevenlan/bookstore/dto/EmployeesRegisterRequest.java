package stevenlan.bookstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeesRegisterRequest {

    private String account;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;
}
