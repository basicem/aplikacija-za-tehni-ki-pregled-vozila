package models;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private boolean admin = false;

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, String password, String userName, LocalDate birthDate, LocalDate hireDate, boolean pristup) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.admin = pristup;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                admin == employee.admin &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(userName, employee.userName) &&
                Objects.equals(password, employee.password) &&
                Objects.equals(birthDate, employee.birthDate) &&
                Objects.equals(hireDate, employee.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, userName, password, birthDate, hireDate, admin);
    }
}
