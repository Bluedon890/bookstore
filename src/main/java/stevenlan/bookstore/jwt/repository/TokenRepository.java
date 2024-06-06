package stevenlan.bookstore.jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import stevenlan.bookstore.jwt.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
            select t from Token t inner join Employees e on t.employees.id = e.id
            where t.employees.id = :employeeId and loggedout = false
            """)
    List<Token> findAllTokenByEmployee(Long employeeId);

    Optional<Token> findByToken(String token);
}
