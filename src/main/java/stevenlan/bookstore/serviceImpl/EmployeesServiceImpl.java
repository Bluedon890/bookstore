package stevenlan.bookstore.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.dto.EmployeesDtoForResponse;
import stevenlan.bookstore.dto.EmployeesIdsRequestDto;
import stevenlan.bookstore.dto.EmployeesResponse;
import stevenlan.bookstore.dto.EmployeesUpdateRequest;
import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.repository.EmployeesRepository;
import stevenlan.bookstore.service.EmlpoyeesService;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmlpoyeesService {

    private final EmployeesRepository employeesRepository;

    private final PasswordEncoder passwordEncoder;

    // 查看多筆資料
    public EmployeesResponse getEmployeesByIds(EmployeesIdsRequestDto ids) {
        List<EmployeesDtoForResponse> employees = new ArrayList<>();

        if (ids.getEmployeesIds() == null) {
            for (Employees employee : employeesRepository.findAll()) {
                employees.add(employeeToEmployeeDto(employee));
            }
            return new EmployeesResponse(null, employees, "查找完成");
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
            return new EmployeesResponse(null, employees, "查詢完畢, id為 " + nonExistId + "的資料不存在");
        } else {
            return new EmployeesResponse(null, employees, "查詢完畢");
        }
    }

    private EmployeesDtoForResponse employeeToEmployeeDto(Employees employee) {
        return EmployeesDtoForResponse.builder().id(employee.getId()).account(employee.getAccount())
                .name(employee.getName())
                .email(employee.getEmail()).phoneNumber(employee.getPhoneNumber()).roles(employee.getRole()).build();
    }

    private Boolean getRoles(String role) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(role));
    }

    // 刪除多筆資料
    public EmployeesResponse deleteEmployeesByIds(EmployeesIdsRequestDto ids) {
        List<EmployeesDtoForResponse> employees = new ArrayList<>();
        if (getRoles("PEOPLE_MANAGER")) {
            if (ids.getEmployeesIds() == null) {
                return new EmployeesResponse(null, null, "請輸入欲刪除的員工id");
            }
            String nonExistId = "";
            for (Long id : ids.getEmployeesIds()) {
                if (employeesRepository.findById(id).isPresent()) {
                    employeesRepository.deleteById(id);
                } else {
                    nonExistId += id;
                    nonExistId += ", ";
                }
            }
            if (!nonExistId.isBlank()) {
                return new EmployeesResponse(null, null, "id為 " + nonExistId + "的資料不存在, 其餘刪除完畢");
            } else {
                return new EmployeesResponse(null, null, "刪除完成");
            }
        } else {
            Employees employee = employeesRepository.findEmployeesByAccount(
                    SecurityContextHolder.getContext().getAuthentication().getName()).get();
            employees.add(employeeToEmployeeDto(employee));
            employeesRepository.deleteById(employee.getId());
            return new EmployeesResponse(null, employees, "您的帳號已刪除");
        }

    }

    // 更新指定資料
    @Transactional
    public EmployeesResponse updateEmployee(EmployeesUpdateRequest req) {
        if (getRoles("PEOPLE_MANAGER")) {
            if (req.getEmployeesId() == null) {
                return new EmployeesResponse(null, null, "請輸入欲更改之id");
            }
            Employees employees = employeesRepository.findById(req.getEmployeesId()).orElse(null);
            if (employees == null) {
                return new EmployeesResponse(null, null, "此id沒有資料, 請輸入正確的員工id");
            }
            if (req.getRoles() != null && !Objects.equals(employees.getRole(), req.getRoles())) {
                employees.setRole(req.getRoles());
            }
            return update(employees, req);
        } else {
            Employees employee = employeesRepository.findEmployeesByAccount(
                    SecurityContextHolder.getContext().getAuthentication().getName()).get();
            return update(employee, req);
        }

    }

    @Transactional
    private EmployeesResponse update(Employees employees, EmployeesUpdateRequest req){
        if (req.getName() != null && req.getName().length() > 0
                && !Objects.equals(employees.getName(), req.getName())) {
            employees.setName(req.getName());
        }
        if (req.getPassword() != null && req.getPassword().length() > 0
                && !Objects.equals(employees.getPassword(), req.getPassword())) {
            employees.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        if (req.getEmail() != null && req.getEmail().length() > 0
                && !Objects.equals(employees.getEmail(), req.getEmail())) {
            employees.setEmail(req.getEmail());
        }
        if (req.getPhoneNumber() != null && req.getPhoneNumber().length() > 0
                && !Objects.equals(employees.getPhoneNumber(), req.getPhoneNumber())) {
            employees.setPhoneNumber(req.getPhoneNumber());
        }
        
        List<EmployeesDtoForResponse> employee = new ArrayList<>();
        employee.add(employeeToEmployeeDto(employees));
        return new EmployeesResponse(null, employee, "更新成功");
    }
}