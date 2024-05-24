package stevenlan.bookstore.employees;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements UserDetailsService{

    private final EmployeesRepository employeesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeesRepository.findEmployeesByAccount(username)
            .orElseThrow(()-> new UsernameNotFoundException("Account not found"));
    }
}
