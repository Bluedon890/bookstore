package stevenlan.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import stevenlan.bookstore.entity.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {

    @Query("SELECT e FROM Employees e WHERE e.account = ?1")
    Optional<Employees> findEmployeesByAccount(String account);

    @Query("SELECT e.id, e.account, e.name, e.email, e.phoneNumber FROM Employees e WHERE e.id = :id")
    List<Object[]> findEmployeeById(Long id);

}
