package stevenlan.bookstore.employees;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.jwt.service.AuthenticationService;
import stevenlan.bookstore.jwt.service.JwtService;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public ArrayList<String> getEmployeesById(List<Long> ids){
        ArrayList<String> employeesInfo = new ArrayList<>();
        for(Long id:ids){

            employeesInfo.add(getEmployeeById(id));
        }
        return employeesInfo;
    }

    public String getEmployeeById(Long id){
		
        List<Object[]> resultList = employeesRepository.findEmployeeById(id);

        String employeeInfo = null;

        for (Object[] result : resultList) {    
            String account = (String) result[0];
            // String password = (String) result[1];
            String name = (String) result[2];
            String email = (String) result[3];
            String phoneNumber = (String) result[4];
            employeeInfo = "Account: " + account + ", Name: " + name + ", Email: " + email + ", Phone Number: " + phoneNumber;
        }
        
        return employeeInfo; 
	}

    // public void addNewEmployees(Employees employees){
    //     Optional<Employees> employeesByAccount = 
    //         employeesRepository.findEmployeesByAccount(employees.getAccount());

    //     if(employeesByAccount.isPresent()){
    //         throw new IllegalStateException("account taken");
    //     }
    //     employeesRepository.save(employees);
    // }
    public void deleteEmployees(List<Long> ids){
        for(Long id : ids){
            deleteEmployeesById(id);
        }
        // return tokenGenerate(request);
    }

    public void deleteEmployeesById(Long employeesId){
        boolean exists = employeesRepository.existsById(employeesId);
        if(!exists){
            throw new IllegalStateException("this id does not exists");
        }
        employeesRepository.deleteById(employeesId);
    }

    @Transactional
    public String updateEmployees(
        Long employeesId, String name, String account, String password, String email, String phoneNumber
                ,HttpServletRequest request
                ){
            Employees employees = employeesRepository.findById(employeesId)
            .orElseThrow(() -> new IllegalStateException("this id does not exists"));

            if(name != null && name.length() > 0 && !Objects.equals(employees.getName(), name)){
                employees.setName(name);
            }
            if(password != null && password.length() > 0 && !Objects.equals(employees.getPassword(), password)){
                employees.setPassword(passwordEncoder.encode(password));
            }
            if(email != null && email.length() > 0 && !Objects.equals(employees.getEmail(), email)){
                employees.setEmail(email);
            }
            if(phoneNumber != null && phoneNumber.length() > 0 && !Objects.equals(employees.getPhoneNumber(), phoneNumber)){
                employees.setPhoneNumber(phoneNumber);
            }
            if(account != null && account.length() > 0 && !Objects.equals(employees.getAccount(), account)){
                employees.setAccount(account);
            }

            return tokenGenerate(request);
        }

    public String tokenGenerate(HttpServletRequest request){

        String authHeader = request.getHeader("Authorization");
        
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Employees employees = employeesRepository.findEmployeesByAccount(username).orElseThrow();
        String newToken = jwtService.generateToken(employees);

        authenticationService.setAllOldTokenLoggedout(employees);

        authenticationService.saveEmployeesToken(newToken, employees);

        return newToken;
    }        
}
