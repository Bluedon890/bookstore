package stevenlan.bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class Bookstore1ApplicationTests {
	
	@Test
	void test(){
		assertThat(true).isTrue();
	}
}
