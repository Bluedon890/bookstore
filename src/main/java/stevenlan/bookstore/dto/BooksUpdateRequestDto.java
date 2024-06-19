package stevenlan.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksUpdateRequestDto{

    //沒有用
    @NotNull(message = "請填要更改的id!")
    //b不能是大寫!!
    private Long bookId;

    private String title;

    private String author;

    private String description;

    private Integer listPrice;
    
    private Integer salePrice;
}
