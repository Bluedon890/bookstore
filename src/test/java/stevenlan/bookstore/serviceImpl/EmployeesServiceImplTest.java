package stevenlan.bookstore.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesUpdateRequest;
import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.entity.Role;
import stevenlan.bookstore.repository.BooksRepository;
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
    EmployeesUpdateRequest employeesUpdateRequest;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        employeesService = new EmployeesServiceImpl(employeesRepository, passwordEncoder);

        employees = new Employees(1L, "2400398", "000000", "Steven", "123", "465", null, null);

        employeesIdsRequestDto = new EmployeesIdsRequestDto("1,2");

        employeesUpdateRequest = new EmployeesUpdateRequest(1L, "password", "bob", "abc", "654321", null);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    // @Test
    // void testGetRoles() {
    //     final Authentication authentication = new TestingAuthenticationToken(
    //             null, null, "USER");
    //     SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    //     Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    //     SecurityContextHolder.setContext(securityContext);
    //     assertThat(employeesService.getRoles("USER")).isTrue();
    // }

    
    @Test
    void testDeleteEmployeesByIds_OneSuccess() {
        final Authentication authentication = new TestingAuthenticationToken(
                null, null, "PEOPLE_MANAGER");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mock(BooksRepository.class, Mockito.CALLS_REAL_METHODS);
        doAnswer(Answers.CALLS_REAL_METHODS).when(employeesRepository).deleteById(any());
        when(employeesRepository.findById(1L)).thenReturn(Optional.of(employees));
        assertThat(employeesService.deleteEmployeesByIds(employeesIdsRequestDto).getMessage()).isEqualTo("id為 2的資料不存在, 其餘刪除完畢");
        verify(employeesRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteEmployeesByIds_NoIds() {
        final Authentication authentication = new TestingAuthenticationToken(
                null, null, "PEOPLE_MANAGER");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mock(BooksRepository.class, Mockito.CALLS_REAL_METHODS);
        doAnswer(Answers.CALLS_REAL_METHODS).when(employeesRepository).deleteById(any());

        employeesIdsRequestDto.setEmployeesIds("");
        assertThat(employeesService.deleteEmployeesByIds(employeesIdsRequestDto).getMessage()).isEqualTo("請輸入欲刪除的員工id");
        verify(employeesRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteEmployeesByIds_User() {
        final Authentication authentication = new TestingAuthenticationToken(
            null, null, "USER");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mock(EmployeesRepository.class);
        when(employeesRepository.findEmployeesByAccount(anyString())).thenReturn(Optional.of(employees));

        mock(EmployeesRepository.class, Mockito.CALLS_REAL_METHODS);
        doAnswer(Answers.CALLS_REAL_METHODS).when(employeesRepository).deleteById(any());
        employeesIdsRequestDto.setEmployeesIds("1L");

        assertThat(employeesService.deleteEmployeesByIds(employeesIdsRequestDto).getMessage()).isEqualTo("您的帳號已刪除");
        verify(employeesRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetEmployeesByIds_GetAll() {
        employeesIdsRequestDto.setEmployeesIds("");
        mock(EmployeesRepository.class);
        when(employeesRepository.findAll()).thenReturn(Collections.singletonList(employees));
        assertThat(employeesService.getEmployeesByIds(employeesIdsRequestDto).getMessage()).isEqualTo("查找完成");
        assertThat(employeesService.getEmployeesByIds(employeesIdsRequestDto).getEmployeesDtos().get(0).getName()).isEqualTo(employees.getName());
    }

    @Test
    void testGetEmployeesByIds() {
        mock(EmployeesRepository.class);
        when(employeesRepository.findById(1L)).thenReturn(Optional.of(employees));
        when(employeesRepository.findById(2L)).thenReturn(Optional.empty());
        assertThat(employeesService.getEmployeesByIds(employeesIdsRequestDto).getMessage()).isEqualTo("查詢完畢, id為 2的資料不存在");
        assertThat(employeesService.getEmployeesByIds(employeesIdsRequestDto).getEmployeesDtos().get(0).getName()).isEqualTo(employees.getName());
    }
    @Test
    void testUpdateEmployee_Success() {
        final Authentication authentication = new TestingAuthenticationToken(
                null, null, "PEOPLE_MANAGER");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        employeesUpdateRequest.setRoles(Collections.singletonList(Role.USER));
        mock(EmployeesRepository.class);
        when(employeesRepository.findById(1L)).thenReturn(Optional.of(employees));
    
        assertThat(employeesService.updateEmployee(employeesUpdateRequest).getMessage()).isEqualTo("更新成功");
        assertThat(employeesService.updateEmployee(employeesUpdateRequest).getEmployeesDtos().get(0).getRoles()).isEqualTo(Collections.singletonList(Role.USER));
    }

    @Test
    void testUpdateEmployee_NoId() {
        final Authentication authentication = new TestingAuthenticationToken(
                null, null, "PEOPLE_MANAGER");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        employeesUpdateRequest.setRoles(Collections.singletonList(Role.USER));

    
        employeesUpdateRequest.setEmployeesId(null);
        assertThat(employeesService.updateEmployee(employeesUpdateRequest).getMessage()).isEqualTo("請輸入欲更改之id");
    }

    @Test
    void testUpdateEmployee_NoExistEmployee() {
        final Authentication authentication = new TestingAuthenticationToken(
                null, null, "PEOPLE_MANAGER");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        employeesUpdateRequest.setRoles(Collections.singletonList(Role.USER));
        mock(EmployeesRepository.class);
        when(employeesRepository.findById(1L)).thenReturn(Optional.empty());
    
        assertThat(employeesService.updateEmployee(employeesUpdateRequest).getMessage()).isEqualTo("此id沒有資料, 請輸入正確的員工id");
    }

    @Test
    void testUpdateEmployee_USER() {
        final Authentication authentication = new TestingAuthenticationToken(
                null, null, "USER");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        employeesUpdateRequest.setRoles(Collections.singletonList(Role.USER));
        mock(EmployeesRepository.class);
        when(employeesRepository.findEmployeesByAccount(anyString())).thenReturn(Optional.of(employees));
        assertThat(employeesService.updateEmployee(employeesUpdateRequest).getMessage()).isEqualTo("更新成功");
        assertThat(employeesService.updateEmployee(employeesUpdateRequest).getEmployeesDtos().get(0).getName()).isEqualTo(employeesUpdateRequest.getName());
    }
}
