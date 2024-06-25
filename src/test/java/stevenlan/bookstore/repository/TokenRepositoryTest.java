package stevenlan.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.entity.Token;
//doesnt matter
@DataJpaTest
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired 
    private EmployeesRepository employeesRepository;

    Token token;
    Employees employees;

    @BeforeEach
    void setUp() {
        employees = new Employees("2400398", "000000", "Steven", "123", "465", null);
        token = new Token("token", false, employees);
        employeesRepository.save(employees);
        tokenRepository.save(token);
    }

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
        employeesRepository.deleteAll();
        employees = null;
        token = null;
    }

    @Test
    void testFindAllTokenByEmployee() {
        assertThat(tokenRepository.findAllTokenByEmployee(employees.getId()).get(0).getEmployees().getAccount()).isEqualTo(employees.getAccount());
       
    }

    @Test
    void testFindByToken() {
        assertThat(tokenRepository.findByToken("token").get().getId()).isEqualTo(1);
    }
}
