package stevenlan.bookstore.config;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.entity.Employees;
import stevenlan.bookstore.entity.Role;
import stevenlan.bookstore.repository.EmployeesRepository;

@Configuration
@RequiredArgsConstructor
public class EmployeesConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(
        EmployeesRepository eRepository
    ){
        return args ->{
            Employees steven = new Employees(
				"2400398",
				passwordEncoder.encode("000000"),
				"steven",
				"2400398@systex.com.tw",
				"123465789",
                Role.ADMIN
			);

    //         Employees alex = new Employees(
	// 			"234",
	// 			"567",
	// 			"alex",
	// 			"890",
	// 			"987654321"
    //         );

            eRepository.saveAll(
                List.of(steven)
            );
        };
    }

}
