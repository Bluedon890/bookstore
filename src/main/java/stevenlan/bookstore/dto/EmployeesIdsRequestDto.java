package stevenlan.bookstore.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
