package stevenlan.bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import stevenlan.bookstore.dto.AuthenticationResponse;
import stevenlan.bookstore.dto.BooksIdsRequest;
import stevenlan.bookstore.dto.BooksRequestDto;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.dto.LoginRequestDto;
import stevenlan.bookstore.entity.Books;
import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.entity.Role;
import stevenlan.bookstore.serviceImpl.JwtService;

@SpringBootTest
// (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Bookstore1ApplicationTests {
	
	@Test
	void test(){
		assertThat(true).isTrue();
	}
	// private static final Logger logger = LoggerFactory.getLogger(Bookstore1ApplicationTests.class);
	
	// @LocalServerPort
	// private int port;
	
	// private String baseURL = "http://localhost";
	
	// private static RestTemplate restTemplate;

	// @Autowired
	// private TestH2BooksRepository testH2BooksRepository;
	// @Autowired
	// private TestH2EmployeesReposutory testH2EmployeesReposutory;
	// @Autowired
	// private JwtService jwtService;
	// @Autowired
	// private PasswordEncoder passwordEncoder;
	// private Employees employees;

	// @BeforeAll
	// public static void beforeClass() {
	// 	restTemplate = new RestTemplate();
	// }

	// @BeforeEach
	// void setUp() {
	// 	baseURL = baseURL.concat(":").concat(port+"").concat("/api/v1/books");
	// 	employees = new Employees(1L, "2400398", passwordEncoder.encode("000000"), "Steven", "123", "789", Collections.singletonList(Role.BOOK_MANAGER), null);
	// 	testH2EmployeesReposutory.save(employees);
	// }

	// @AfterEach
	// void tearDown() {
		
	// }

	// @Test
	// @WithMockUser
	// public void testAddNewBooks(){
	// 	String URL = baseURL.concat("/add");
	// 	BooksRequestDto booksRequestDto = new BooksRequestDto("apple", "anson", 
    //             "apple is red", 10, 20);
	// 	ResponseEntity<BooksResponse> response = restTemplate.postForEntity(URL, booksRequestDto, BooksResponse.class);
	// 	assertThat(response.getBody().getBooks().get(0).getTitle()).isEqualTo("apple");
	// 	assertThat(response.getStatusCode()).isEqualTo(status().isOk());
	// 	assertThat(testH2BooksRepository.findAll().size()).isEqualTo(1);
	// }
	
	// @Test
	// public void testGetBooks(){
	// 	String thisURL = "http://localhost";
	// 	thisURL = thisURL.concat(":").concat(port+"").concat("/v1/login");
	// 	LoginRequestDto loginRequestDto = new LoginRequestDto("2400398", "000000");
	// 	ResponseEntity<AuthenticationResponse> loginResponse = restTemplate.postForEntity(thisURL, loginRequestDto, AuthenticationResponse.class);

	// 	HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);
    //     headers.setBearerAuth(loginResponse.getBody().getToken());
		
		
	// 	String URL = baseURL.concat("/get");
	// 	Books books = new Books("apple", "anson", 
    //             "apple is red", 10, 20);
	// 	testH2BooksRepository.save(books);
	// 	BooksIdsRequest booksIdsRequest = new BooksIdsRequest("");
	// 	HttpEntity<BooksIdsRequest> entity = new HttpEntity<>(booksIdsRequest, headers);
	// 	ResponseEntity<BooksResponse> response = restTemplate.exchange(
	// 		URL,
	// 		HttpMethod.POST,
	// 		entity,
	// 		BooksResponse.class);
	// 	assertThat(response.getBody().getBooks().get(0).getTitle()).isEqualTo(null);
		
		
	// 	assertThat(response.getStatusCode()).isEqualTo(status().isOk());
	// 	assertThat(testH2BooksRepository.findAll().size()).isEqualTo(1);
	// 	assertThat(testH2EmployeesReposutory.findAll().size()).isEqualTo(1);
	// }

	// @Test
	// public void testLogin(){
	// 	String thisURL = "http://localhost";
	// 	thisURL = thisURL.concat(":").concat(port+"").concat("/v1/login");
	// 	LoginRequestDto loginRequestDto = new LoginRequestDto("2400398", "000000");
	// 	ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(thisURL, loginRequestDto, AuthenticationResponse.class);
	// 	assertThat(response.getBody().getToken()).isNotEmpty();
	// }
}
