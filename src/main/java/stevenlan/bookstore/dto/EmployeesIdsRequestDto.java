package stevenlan.bookstore.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeesIdsRequestDto {

    private String employeesIds;

    public List<Long> getEmployeesIds() {
        if(employeesIds.isEmpty()||employeesIds==""){
            return null;
        }
        List<Long> Ids = Arrays.stream(employeesIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        return Ids;
    }

}
