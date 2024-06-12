package stevenlan.bookstore.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stevenlan.bookstore.entity.Role;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesDtoForResponse {

    private Long id;

    private String account;

    private String name;

    private String email;

    private String phoneNumber;

    private List<Role> roles;
}
