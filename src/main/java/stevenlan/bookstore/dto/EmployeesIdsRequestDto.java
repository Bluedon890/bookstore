package stevenlan.bookstore.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Setter;

@Setter
public class EmployeesIdsRequestDto extends HttpServletRequestDto {

    private String employeesIds;

    public List<Long> getEmployeesIds() {
        List<Long> Ids = Arrays.stream(employeesIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        return Ids;
    }

}
