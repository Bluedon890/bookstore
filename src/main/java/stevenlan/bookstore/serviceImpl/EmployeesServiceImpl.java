package stevenlan.bookstore.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.EmployeesDto;
import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.jwt.entity.AuthenticationResponse;
import stevenlan.bookstore.jwt.service.AuthenticationService;
import stevenlan.bookstore.jwt.service.JwtService;
import stevenlan.bookstore.repository.EmployeesRepository;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl {

    private final EmployeesRepository employeesRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    // 查看多筆資料

    public EmployeesResponse getEmployeesByIds(EmployeesIdsRequestDto req) {
        List<EmployeesDto> employees= new ArrayList<>();

        for (Long id : req.getEmployeesIds()) {
            Employees employee = employeesRepository.findById(id).orElseThrow();
            employees.add(employeeToEmployeeDto(employee));
        }
        
        return new EmployeesResponse(employees, "查找完成", 
        tokenGenerateNew(
        SecurityContextHolder.getContext().getAuthentication().getName())
        );

    }

    private EmployeesDto employeeToEmployeeDto(Employees employee) {
        return EmployeesDto.builder().
            id(employee.getId()).
            account(employee.getAccount()).
            name(employee.getName()).
            email(employee.getEmail()).
            phoneNumber(employee.getPhoneNumber()).build();
    }

    // 刪除多筆資料
    @PreAuthorize("hasAuthority('PEOPLE_MANAGER')")
    public String deleteEmployeesByIds(List<Long> ids, HttpServletRequest request) {
        for (Long id : ids) {
            deleteEmployeeById(id);
        }
        return tokenGenerate(request);
    }

    // 刪除單筆資料
    public void deleteEmployeeById(Long employeesId) {
        boolean exists = employeesRepository.existsById(employeesId);
        if (!exists) {
            throw new IllegalStateException("this id does not exists");
        }
        employeesRepository.deleteById(employeesId);
    }

    @Transactional
    @PreAuthorize("hasAuthority('PEOPLE_MANAGER')")
    public AuthenticationResponse updateEmployee(
            Long employeesId, String name, String account, String password, String email, String phoneNumber,
            HttpServletRequest request) {
        Employees employees = employeesRepository.findById(employeesId)
                .orElseThrow(() -> new IllegalStateException("this id does not exists"));

        if (name != null && name.length() > 0 && !Objects.equals(employees.getName(), name)) {
            employees.setName(name);
        }
        if (password != null && password.length() > 0 && !Objects.equals(employees.getPassword(), password)) {
            employees.setPassword(passwordEncoder.encode(password));
        }
        if (email != null && email.length() > 0 && !Objects.equals(employees.getEmail(), email)) {
            employees.setEmail(email);
        }
        if (phoneNumber != null && phoneNumber.length() > 0
                && !Objects.equals(employees.getPhoneNumber(), phoneNumber)) {
            employees.setPhoneNumber(phoneNumber);
        }
        if (account != null && account.length() > 0 && !Objects.equals(employees.getAccount(), account)) {
            employees.setAccount(account);
        }

        return new AuthenticationResponse(tokenGenerate(request), "更新成功");
    }

    

    public String tokenGenerate(HttpServletRequest request) {

        Employees employees = findEmployeeByRequest(request);
        String newToken = jwtService.generateToken(employees);

        authenticationService.setAllOldTokenLoggedout(employees);

        authenticationService.saveEmployeesToken(newToken, employees);

        return newToken;
    }

    public String tokenGenerateNew(String account) {

        Employees employees = employeesRepository.findEmployeesByAccount(account).orElseThrow();
        String newToken = jwtService.generateToken(employees);

        authenticationService.setAllOldTokenLoggedout(employees);

        authenticationService.saveEmployeesToken(newToken, employees);

        return newToken;
    }

    // 從httprequest中取得token中的username並查詢
    public Employees findEmployeeByRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Employees employees = employeesRepository.findEmployeesByAccount(username).orElseThrow();
        return employees;
    }
}
