package stevenlan.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import stevenlan.bookstore.entity.Employees;

@DataJpaTest
public class EmployeesRespositoryTest {

    @Autowired
    private EmployeesRepository employeesRepository;
    Employees employees;
    @BeforeEach
    void setUp() {
        employees = new Employees(1L, "2400398", "000000", "Steven", "123", "465", null, null);
        employeesRepository.save(employees);
    }
    @AfterEach
    void tearDown() {
        employees = null;
        employeesRepository.deleteAll();
    }

    //成功
    @Test
    void testFindEmployeesByAccount_Found(){
        Optional<Employees> foundEmployee = employeesRepository.findEmployeesByAccount("2400398");
        assertThat(foundEmployee.get().getId()).isEqualTo(employees.getId());
        assertThat(foundEmployee.get().getPhoneNumber()).isEqualTo(employees.getPhoneNumber());
    }
    
    //失敗
    @Test
    void testFindEmployeesByAccount_NotFound(){
        Optional<Employees> foundEmployee = employeesRepository.findEmployeesByAccount("123456789");
        assertThat(foundEmployee.isEmpty()).isTrue();
    }
}
