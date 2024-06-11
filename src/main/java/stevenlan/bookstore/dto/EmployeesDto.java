package stevenlan.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesDto {

    private Long id;

    private String account;

    private String name;

    private String email;

    private String phoneNumber;
}
