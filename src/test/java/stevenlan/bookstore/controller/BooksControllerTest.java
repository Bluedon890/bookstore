package stevenlan.bookstore.controller;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import stevenlan.bookstore.service.BooksService;
import stevenlan.bookstore.serviceImpl.EmployeesDetailsService;
import stevenlan.bookstore.serviceImpl.JwtService;


//忽略此測試


@ExtendWith(SpringExtension.class)
@WebMvcTest(BooksController.class)
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.aop.AopAutoConfiguration")
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksService booksService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private EmployeesDetailsService employeesDetailsService;

    @Test
    @WithMockUser(roles = "USER")
    void testAddNewBooks() throws Exception {
        Mockito.doNothing().when(booksService.addNewBooks(any()));

        this.mockMvc.perform(
                post("api/v1/books/get")

        ).andExpect(
                status().isOk());
    }

    @Test
    void testDeleteBooks() {

    }

    @Test
    void testGetBooksByIds() {

    }

    @Test
    void testUpdateBooks() {

    }
}
