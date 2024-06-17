package stevenlan.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksRequestDto {

    @NotNull(message = "請輸入書名")
    @Size(min = 1, message = "請輸入書名")
    private String title;

    private String author;

    private String description;

    private Integer listPrice;
    
    private Integer salePrice;

    public boolean isTitleEmpty() {
        return title == null || title.isEmpty();
    }
}
