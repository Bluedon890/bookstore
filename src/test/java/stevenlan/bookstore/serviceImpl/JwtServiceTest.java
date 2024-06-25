package stevenlan.bookstore.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.entity.Token;
import stevenlan.bookstore.repository.TokenRepository;

public class JwtServiceTest {
    
    @Mock
    private TokenRepository tokenRepository;

    private JwtService jwtService;
    private AutoCloseable autoCloseable;
    private Employees employees;
    @BeforeEach
    void setUp() {

        autoCloseable = MockitoAnnotations.openMocks(this);
        jwtService = new JwtService(tokenRepository);
        employees = new Employees(1L, "2400398", "000000", "Steven", "123", "465", null, null);

    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken(employees);
        assertThat(jwtService.extractUsername(token)).isEqualTo(employees.getAccount());
    }

    @Test
    void testGenerateToken() {
        assertThat(jwtService.generateToken(employees)).isNotNull();
    }

    @Test
    void testIsValid_True() {
        String token = jwtService.generateToken(employees);
        mock(Token.class);
        Token tokenEntity = new Token(1, "token", false, employees);
        mock(TokenRepository.class);
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(tokenEntity));

        assertThat(jwtService.isValid(token, employees)).isTrue();
    }

    @Test
    void testIsValid_False() {
        String token = jwtService.generateToken(employees);
        mock(Token.class);
        Token tokenEntity = new Token(1, "token", true, employees);
        mock(TokenRepository.class);
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(tokenEntity));

        assertThat(jwtService.isValid(token, employees)).isFalse();
    }
}
