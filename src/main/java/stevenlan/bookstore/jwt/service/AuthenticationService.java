package stevenlan.bookstore.jwt.service;



import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import stevenlan.bookstore.employees.Employees;
import stevenlan.bookstore.employees.EmployeesRepository;
import stevenlan.bookstore.employees.Role;
import stevenlan.bookstore.jwt.entity.AuthenticationResponse;
import stevenlan.bookstore.jwt.entity.Token;
import stevenlan.bookstore.jwt.repository.TokenRepository;

@Service
public class AuthenticationService {

    private final EmployeesRepository empRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    

   

    public AuthenticationService(EmployeesRepository empRepository, 
                                PasswordEncoder passwordEncoder, 
                                JwtService jwtService,
                                AuthenticationManager authenticationManager, 
                                TokenRepository tokenRepository) {
        this.empRepository = empRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    //管理層註冊
    public AuthenticationResponse adminRegister(Employees request) {

        if(empRepository.findEmployeesByAccount(request.getUsername()).isPresent()){
            return new AuthenticationResponse(null, "此帳號已存在");
        }
        Employees employees = new Employees();
        employees.setAccount(request.getAccount());
        employees.setName(request.getName());
        employees.setEmail(request.getEmail());
        employees.setPhoneNumber(request.getPhoneNumber());
        employees.setPassword(passwordEncoder.encode(request.getPassword()));


        employees.setRole(request.getRole());

        employees = empRepository.save(employees);

        String jwt = jwtService.generateToken(employees);

        

        saveEmployeesToken(jwt, employees);

        return new AuthenticationResponse(jwt, "註冊成功");

    }
    //員工註冊
    public AuthenticationResponse employeesRegister(Employees request) {

        if(empRepository.findEmployeesByAccount(request.getUsername()).isPresent()){
            return new AuthenticationResponse(null, "此帳號已存在");
        }
        Employees employees = new Employees();
        employees.setAccount(request.getAccount());
        employees.setName(request.getName());
        employees.setEmail(request.getEmail());
        employees.setPhoneNumber(request.getPhoneNumber());
        employees.setPassword(passwordEncoder.encode(request.getPassword()));


        employees.setRole(Role.USER);

        employees = empRepository.save(employees);

        String jwt = jwtService.generateToken(employees);

        

        saveEmployeesToken(jwt, employees);

        return new AuthenticationResponse(jwt, "註冊成功");

    }

    //登入
    public AuthenticationResponse authenticate(Employees request) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getUsername(),
                       request.getPassword()
               )
       );

       Employees employees = empRepository.findEmployeesByAccount(request.getUsername()).orElseThrow();
       String token = jwtService.generateToken(employees);

       setAllOldTokenLoggedout(employees);

       saveEmployeesToken(token, employees);

       return new AuthenticationResponse(token, "登入成功");
    }
    
    //存token
    private void saveEmployeesToken(String jwt, Employees employees) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedout(false);
        token.setEmployees(employees);
        tokenRepository.save(token);
    }

    //在產生新token前將舊的設定為loggedout
    private void setAllOldTokenLoggedout(Employees employees) {
        List<Token> validTokensList = tokenRepository.findAllTokenByEmployee(employees.getId());
        if(!validTokensList.isEmpty()){
            validTokensList.forEach(t->{
                t.setLoggedout(true);
            });
        }
        tokenRepository.saveAll(validTokensList);
    }
}
