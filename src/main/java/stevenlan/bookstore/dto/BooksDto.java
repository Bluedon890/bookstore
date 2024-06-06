package stevenlan.bookstore.dto;

public class BooksDto {

    private Long id;
    private String title;
    private String author;
    private String description;
    private Integer listPrice;
    private Integer salePrice;

    public BooksDto(Long id, String title, String author, String description, Integer listPrice, Integer salePrice) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
    }

    public BooksDto(String title, String author, String description, Integer listPrice, Integer salePrice) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
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

}
