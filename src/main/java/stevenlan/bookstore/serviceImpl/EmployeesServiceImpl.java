package stevenlan.bookstore.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.BooksResponse;
import stevenlan.bookstore.dto.EmployeesDtoForResponse;
import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.dto.EmployeesUpdateRequest;
import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.jwt.entity.AuthenticationResponse;
import stevenlan.bookstore.jwt.service.AuthenticationService;
import stevenlan.bookstore.jwt.service.JwtService;
import stevenlan.bookstore.repository.EmployeesRepository;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl {

    private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    private final EmployeesRepository employeesRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    // 查看多筆資料

    public EmployeesResponse getEmployeesByIds(EmployeesIdsRequestDto ids) {
        List<EmployeesDtoForResponse> employees = new ArrayList<>();

        if(ids.getEmployeesIds() == null){
            for(Employees employee : employeesRepository.findAll()){
                employees.add(employeeToEmployeeDto(employee));
            }
            return new EmployeesResponse(employees, "查找完成", null);
        }

        String nonExistId = "";
        for (Long id : ids.getEmployeesIds()) {
            if (employeesRepository.findById(id).isPresent()) {
                employees.add(employeeToEmployeeDto(employeesRepository.findById(id).orElseThrow()));
            } else {
                nonExistId += id;
                nonExistId += ", ";
            }
        }
        if (!nonExistId.isBlank()) {
        return new EmployeesResponse(employees, "查詢完畢, id為 " + nonExistId + "的資料不存在", null);
        }else{
            return new EmployeesResponse(employees, "查詢完畢", null);
        }
    }

    private EmployeesDtoForResponse employeeToEmployeeDto(Employees employee) {
        return EmployeesDtoForResponse.builder().id(employee.getId()).account(employee.getAccount()).name(employee.getName())
                .email(employee.getEmail()).phoneNumber(employee.getPhoneNumber()).roles(employee.getRole()).build();
    }

    // 刪除多筆資料
    @PreAuthorize("hasAuthority('PEOPLE_MANAGER')")
    public EmployeesResponse deleteEmployeesByIds(EmployeesIdsRequestDto ids) {

        String nonExistId = "";
        for (Long id : ids.getEmployeesIds()) {
            if (employeesRepository.findById(id).isPresent()) {
                deleteEmployeeById(id);
            } else {
                nonExistId += id;
                nonExistId += ", ";
        }}
        if (!nonExistId.isBlank()) {
            return new EmployeesResponse(null, "id為 " + nonExistId + "的資料不存在, 其餘刪除完畢", null);
        }else{
        return new EmployeesResponse(null, "刪除完成", null);
        }
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
    public EmployeesResponse updateEmployee(EmployeesUpdateRequest req) {

        logger.info("id呢我is呢操" + req);

        Employees employees = employeesRepository.findById(req.getEmployeesId()).orElse(null);
        if(employees==null){
            return new EmployeesResponse(null, null, "此id沒有資料, 請輸入正確的員工id");
        }

        if (req.getName() != null && req.getName().length() > 0 && !Objects.equals(employees.getName(), req.getName())) {
            employees.setName(req.getName());
        }
        if (req.getPassword() != null && req.getPassword().length() > 0 && !Objects.equals(employees.getPassword(), req.getPassword())) {
            employees.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        if (req.getEmail() != null && req.getEmail().length() > 0 && !Objects.equals(employees.getEmail(), req.getEmail())) {
            employees.setEmail(req.getEmail());
        }
        if (req.getPhoneNumber() != null && req.getPhoneNumber().length() > 0
                && !Objects.equals(employees.getPhoneNumber(), req.getPhoneNumber())) {
            employees.setPhoneNumber(req.getPhoneNumber());
        }
        if (req.getAccount() != null && req.getAccount().length() > 0 && !Objects.equals(employees.getAccount(), req.getAccount())) {
            employees.setAccount(req.getAccount());
        }
        if (req.getRoles() != null && !Objects.equals(employees.getRole(), req.getRoles())) {
            employees.setRole(req.getRoles());
        }
        List<EmployeesDtoForResponse> employee = new ArrayList<>();
        employee.add(employeeToEmployeeDto(employees));
        return new EmployeesResponse(employee,"更新成功",null);
    }

    public String tokenGenerate(HttpServletRequest request) {

        Employees employees = findEmployeeByRequest(request);
        String newToken = jwtService.generateToken(employees);

        authenticationService.setAllOldTokenLoggedout(employees);

        authenticationService.saveEmployeesToken(newToken, employees);

        return newToken;
    }

    // 從contextholder中取account來生成新token
    public String tokenGenerateNew() {

        String account = SecurityContextHolder.getContext().getAuthentication().getName();

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
