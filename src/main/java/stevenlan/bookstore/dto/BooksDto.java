package stevenlan.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BooksDto {

    private Long id;

    private String title;

    private String author;

    private String description;

    private Integer listPrice;
    
    private Integer salePrice;

}
