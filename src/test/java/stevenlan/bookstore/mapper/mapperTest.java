// package stevenlan.bookstore.mapper;

// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;

// import lombok.RequiredArgsConstructor;
// import stevenlan.bookstore.dto.BooksDto;
// import stevenlan.bookstore.entity.Books;


// public class mapperTest {

//     @Autowired
//     ProductMapper mapper;
// @Test
// public void shouldMapCarToDto() {
//     //given
//     Books book = Books.builder()
//     .title("apple")
//     .author("who")
//     .description("apple is red")
//     .listPrice(20)
//     .salePrice(15).build();
 
//     //when
//     BooksDto booksDto = mapper.booksToBooksDto(book);
 
//     //then
//     Assertions.assertThat( booksDto ).isNotNull();
//     Assertions.assertThat( booksDto.getTitle() ).isEqualTo( "apple" );
//     Assertions.assertThat( booksDto.getAuthor() ).isEqualTo( "who" );
//     Assertions.assertThat( booksDto.getDescription() ).isEqualTo( "apple is red" );
//     Assertions.assertThat( booksDto.getListPrice() ).isEqualTo( 20 );
//     Assertions.assertThat( booksDto.getSalePrice() ).isEqualTo( 15 );
    
// }
// }