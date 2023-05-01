package dao;

import models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeesDAOTest {

    private EmployeeDAO dao;
    @BeforeEach
    public void resetujBazu() throws SQLException {
        InitDB initDB = new InitDB();
        try {
            initDB.delete();
        } catch (SQLException e) {

        }
        initDB.createDB();
        dao = new EmployeeDAO();
    }

    @Test
    void addEmployee() {
        Employee employee = dao.getEmployeeWithUserName("misomisic");
        assertEquals("Mišo", employee.getFirstName());
        assertEquals("Mišić", employee.getLastName());

        Employee employee1 = new Employee();
        employee1.setFirstName("Ime");
        employee1.setLastName("Prezime");
        employee1.setPassword("lozinka");
        employee1.setPassword("username");
        employee1.setBirthDate(LocalDate.now().minusYears(30));
        employee1.setBirthDate(LocalDate.now());
        dao.addEmployee(employee1);

        ArrayList<Employee> employees = dao.employees();
        assertEquals("Ime", employees.get(5).getFirstName());
    }

    @Test
    void deleteEmployee() {
        Employee employee = dao.getEmployeeWithUserName("misomisic");
        assertEquals("Mišo", employee.getFirstName());
        assertEquals("Mišić", employee.getLastName());
        dao.deleteEmployee(employee);

        ArrayList<Employee> employees = dao.employees();
        assertEquals(4, employees.size());

        assertEquals("mehomehic", employees.get(0).getUserName());
        assertEquals("dadodadic", employees.get(1).getUserName());
        assertEquals("lanalanic", employees.get(2).getUserName());
        assertEquals("darkodarkic", employees.get(3).getUserName());
    }

    @Test
    void updateEmployee() {
        Employee employee = dao.getEmployeeWithUserName("misomisic");
        employee.setUserName("imeprezime");
        dao.updateEmployee(employee);

        ArrayList<Employee> employees = dao.employees();
        assertEquals("imeprezime", employees.get(4).getUserName());
    }
}
