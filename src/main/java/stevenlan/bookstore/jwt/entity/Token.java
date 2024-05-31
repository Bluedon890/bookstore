package stevenlan.bookstore.jwt.entity;

import jakarta.persistence.*;
import stevenlan.bookstore.entity.Employees;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "is_logged_out")
    private boolean loggedout;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "employees_id")
    private Employees employees;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLoggedout() {
        return loggedout;
    }

    public void setLoggedout(boolean loggedout) {
        this.loggedout = loggedout;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    

    
}
