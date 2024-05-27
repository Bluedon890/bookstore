package stevenlan.bookstore.employees;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployeesRepository employeesRepository;
    private final PasswordEncoder passwordEncoder;

    
    

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
            String password = (String) result[1];
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
    }

    public void deleteEmployeesById(Long employeesId){
        boolean exists = employeesRepository.existsById(employeesId);
        if(!exists){
            throw new IllegalStateException("this id does not exists");
        }
        employeesRepository.deleteById(employeesId);
    }

    @Transactional
    public void updateEmployees(
        Long employeesId, String name, String account, String password, String email, String phoneNumber){
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
        }
}
