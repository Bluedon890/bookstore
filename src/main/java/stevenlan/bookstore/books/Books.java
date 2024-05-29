package stevenlan.bookstore.books;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
@Table(name = "Books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // @Column(name = "書本id")
    private Long id;
    private String title;
    private String author;
    private String description;
    private Integer listPrice;
    private Integer salePrice;

    public Books(String title, String author, String description, Integer listPrice, Integer salePrice) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
    }

    public Books(Long id, String title, String author, String description, Integer listPrice, Integer salePrice) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
    }

    public Books() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getListPrice() {
        return listPrice;
    }

    public void setListPrice(Integer listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "Books [id=" + id + ", title=" + title + ", author=" + author + ", description=" + description
                + ", listPrice=" + listPrice + ", salePrice=" + salePrice + "]";
    }
    

}
