package stevenlan.bookstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BooksRequestDto {

    private String title;

    private String author;

    private String description;

    private Integer listPrice;
    
    private Integer salePrice;

}
