package stevenlan.bookstore.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import stevenlan.bookstore.dto.EmployeesRegisterRequest;
import stevenlan.bookstore.dto.EmployeesRequestDto;
import stevenlan.bookstore.dto.LoginRequestDto;
import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.repository.EmployeesRepository;
import stevenlan.bookstore.repository.TokenRepository;
import stevenlan.bookstore.entity.Token;

public class AuthenticationServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private EmployeesRepository employeesRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;
    
    private AuthenticationService authenticationService;
    
    AutoCloseable autoCloseable;
    LoginRequestDto loginRequestDto;
    Employees employees;
    EmployeesRegisterRequest employeesRegisterRequest;
    EmployeesRequestDto employeesRequestDto;
    
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        authenticationService = new AuthenticationService(employeesRepository, passwordEncoder, jwtService, authenticationManager, tokenRepository);
    
        loginRequestDto = new LoginRequestDto("2400398", "000000");
    
        employees = new Employees(1L, "2400398", "000000", "Steven", "123", "465", null, null);
    
        employeesRegisterRequest = new EmployeesRegisterRequest("2400398", "000000", "Steven", "123", "465");
    
        employeesRequestDto = new EmployeesRequestDto("2400398", "000000", "Steven", "123", "465", null);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void testAuthenticate_Success() {
        mock(EmployeesRepository.class);
        when(employeesRepository.findEmployeesByAccount("2400398")).thenReturn(Optional.of(employees));
        mock(JwtService.class);
        when(jwtService.generateToken(employees)).thenReturn("token");
        mock(TokenRepository.class);
        when(tokenRepository.findAllTokenByEmployee(1L)).thenReturn(new ArrayList<>());
        mock(Token.class);
        Token token = new Token(1, "token", false, employees);
        when(tokenRepository.save(any())).thenReturn(token);

        assertThat(authenticationService.authenticate(loginRequestDto).getMessage()).isEqualTo("登入成功");
        assertThat(authenticationService.authenticate(loginRequestDto).getToken()).isEqualTo("token");
    }

    @Test
    void testEmployeesRegister_Success() {
        mock(EmployeesRepository.class);
        when(employeesRepository.findEmployeesByAccount("2400398")).thenReturn(Optional.empty());
        mock(Employees.class);
        when(employeesRepository.save(any())).thenReturn(employees);
        mock(JwtService.class);
        when(jwtService.generateToken(employees)).thenReturn("token");
        mock(TokenRepository.class);
        when(tokenRepository.save(any())).thenReturn(null);
        assertThat(authenticationService.employeesRegister(employeesRegisterRequest).getMessage()).isEqualTo("註冊成功");
        assertThat(authenticationService.employeesRegister(employeesRegisterRequest).getToken()).isEqualTo("token");
    }

    @Test
    void testEmployeesRegister_AlreadyExist() {
        mock(EmployeesRepository.class);
        when(employeesRepository.findEmployeesByAccount("2400398")).thenReturn(Optional.of(employees));
        
        assertThat(authenticationService.employeesRegister(employeesRegisterRequest).getMessage()).isEqualTo("此帳號已存在");
        assertThat(authenticationService.employeesRegister(employeesRegisterRequest).getToken()).isEqualTo(null);
    }

    @Test
    void testPmRegister_Success() {
        mock(EmployeesRepository.class);
        when(employeesRepository.findEmployeesByAccount("2400398")).thenReturn(Optional.empty());
        mock(Employees.class);
        when(employeesRepository.save(any())).thenReturn(employees);
        mock(JwtService.class);
        when(jwtService.generateToken(employees)).thenReturn("token");
        mock(TokenRepository.class);
        when(tokenRepository.save(any())).thenReturn(null);
        assertThat(authenticationService.pmRegister(employeesRequestDto).getMessage()).isEqualTo("註冊成功");
        assertThat(authenticationService.pmRegister(employeesRequestDto).getToken()).isEqualTo("token");
    }

    @Test
    void testPmRegister_AlreadyExist() {
        mock(EmployeesRepository.class);
        when(employeesRepository.findEmployeesByAccount("2400398")).thenReturn(Optional.of(employees));
        
        assertThat(authenticationService.pmRegister(employeesRequestDto).getMessage()).isEqualTo("此帳號已存在");
        assertThat(authenticationService.pmRegister(employeesRequestDto).getToken()).isEqualTo(null);
    }

    @Test
    void testTokenGenerateTokenFromContextholder_Success() {
        final Authentication authentication = new TestingAuthenticationToken(
                null, null, "PEOPLE_MANAGER");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mock(EmployeesRepository.class);
        when(employeesRepository.findEmployeesByAccount(anyString())).thenReturn(Optional.of(employees));
        mock(JwtService.class);
        when(jwtService.generateToken(employees)).thenReturn("token");
        mock(TokenRepository.class);
        when(tokenRepository.save(any())).thenReturn(null);
        assertThat(authenticationService.tokenGenerateTokenFromContextholder()).isEqualTo("token");
    }

    @Test
    void testTokenGenerateTokenFromContextholde_AlreadyExist() {
        final Authentication authentication = new TestingAuthenticationToken(
                null, null, "PEOPLE_MANAGER");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mock(EmployeesRepository.class);
        when(employeesRepository.findEmployeesByAccount(anyString())).thenReturn(Optional.empty());
        
        assertThat(authenticationService.tokenGenerateTokenFromContextholder()).isEqualTo("請重新登入");
        
    }
}
