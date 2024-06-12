package stevenlan.bookstore.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BooksIdsRequest{

    private String booksIds;

    
    public List<Long> getBooksIds() {
        if(booksIds.isEmpty()||booksIds==""){
            return null;
        }
        List<Long> Ids = Arrays.stream(booksIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        return Ids;
    }
}
