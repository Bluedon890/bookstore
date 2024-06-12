package stevenlan.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BooksUpdateRequestDto{

    @NotNull(message = "請填要更改的id!")
    private Long BookId;

    private BooksRequestDto booksRequestDto;
}
