package dao;

import models.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeDAO extends BaseDAO {
    private PreparedStatement employeesStatement, getEmployeeWithUserName, addEmployeeStatement,
            getIDEmployeeStatement, deleteEmployeeStatement, updateEmployeeStatement;

    protected void prepareStatements() {
        try {
            employeesStatement = dbConnection.getSession().prepareStatement("SELECT * FROM employee");
            getEmployeeWithUserName = dbConnection.getSession().prepareStatement("SELECT * FROM employee WHERE user_name=?");
            addEmployeeStatement = dbConnection.getSession().prepareStatement("INSERT INTO employee VALUES(?,?,?,?,?,?,?,?)");
            getIDEmployeeStatement = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM employee");
            deleteEmployeeStatement = dbConnection.getSession().prepareStatement("DELETE FROM employee WHERE id=?");
            updateEmployeeStatement = dbConnection.getSession().prepareStatement("UPDATE employee SET first_name=?, last_name=?, password=?, user_name=?, birth_date=?, hire_date=?, admin=? WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Employee> employees() {
        ArrayList<Employee> rezultat = new ArrayList();
        try {
            ResultSet rs = employeesStatement.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                rezultat.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void addEmployee(Employee employee) {
        try {
            ResultSet rs = getIDEmployeeStatement.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            employee.setId(id);

            addEmployeeStatement.setInt(1, employee.getId());
            addEmployeeStatement.setString(2, employee.getFirstName());
            addEmployeeStatement.setString(3, employee.getLastName());
            addEmployeeStatement.setString(4, employee.getPassword());
            addEmployeeStatement.setString(5, employee.getUserName());
            addEmployeeStatement.setString(6, employee.getBirthDate().toString());
            addEmployeeStatement.setString(7, employee.getBirthDate().toString());
            addEmployeeStatement.setBoolean(8, employee.isAdmin());

            addEmployeeStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserNameTaken(String userName, Employee employee) {
        try {
            getEmployeeWithUserName.setString(1, userName);
            ResultSet resultSet = getEmployeeWithUserName.executeQuery();
            if(resultSet.next()) {
                if(employee == null) return false;
                if(resultSet.getInt(1) == employee.getId() && resultSet.getString(5).equals(employee.getUserName())) {
                    return true;
                }
                return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Employee getEmployeeWithUserName(String userName) {
        try {
            getEmployeeWithUserName.setString(1, userName);
            ResultSet rs = getEmployeeWithUserName.executeQuery();
            if(rs.next()) {
                Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteEmployee(Employee employee) {
        try {
            deleteEmployeeStatement.setInt(1, employee.getId());
            deleteEmployeeStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        try {
            updateEmployeeStatement.setString(1, employee.getFirstName());
            updateEmployeeStatement.setString(2, employee.getLastName());
            updateEmployeeStatement.setString(3, employee.getPassword());
            updateEmployeeStatement.setString(4, employee.getUserName());
            updateEmployeeStatement.setString(5, employee.getBirthDate().toString());
            updateEmployeeStatement.setString(6, employee.getHireDate().toString());
            updateEmployeeStatement.setBoolean(7, employee.isAdmin());
            updateEmployeeStatement.setInt(8, employee.getId());
            updateEmployeeStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
