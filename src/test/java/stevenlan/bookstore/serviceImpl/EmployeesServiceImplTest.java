package stevenlan.bookstore.serviceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.repository.EmployeesRepository;
import stevenlan.bookstore.service.EmployeesService;


public class EmployeesServiceImplTest {

    @Mock
    private EmployeesRepository employeesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private EmployeesService employeesService;
    
    AutoCloseable autoCloseable;
    Employees employees;
    EmployeesIdsRequestDto employeesIdsRequestDto;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        employeesService = new EmployeesServiceImpl(employeesRepository, passwordEncoder);

        employees = new Employees(1L, "2400398", "000000", "Steven", "123", "465", null, null);
        
        employeesIdsRequestDto = new EmployeesIdsRequestDto("1");
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void testDeleteEmployeesByIds() {
        

    }
    

    @Test
    void testGetEmployeesByIds() {

    }

    @Test
    void testUpdateEmployee() {

    }
}
