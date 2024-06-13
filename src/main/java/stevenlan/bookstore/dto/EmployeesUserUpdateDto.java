package stevenlan.bookstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeesUserUpdateDto {

    private String password;

    private String name;

    private String email;

    private String phoneNumber;
}
