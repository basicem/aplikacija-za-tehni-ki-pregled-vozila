package controllers;

import dao.EmployeeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Employee;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;


public class EmployeeController {
    private EmployeeDAO employeeDAO;
    private Employee employee;

    public TextField fldFirstName;
    public TextField fldLastName;
    public PasswordField fldPassword;
    public TextField fldUserName;
    public Label takenUserName;
    public DatePicker fldBirthDate;
    public  DatePicker fldHireDate;
    public RadioButton admin;

    public EmployeeController(Employee employee) {
        employeeDAO = new EmployeeDAO();
        this.employee = employee;
    }

    @FXML
    public void initialize() {
        takenUserName.setVisible(false);
        admin.setSelected(false);
        if(employee != null) {
            fldFirstName.setText(employee.getFirstName());
            fldLastName.setText(employee.getLastName());
            fldPassword.setText(employee.getPassword());
            fldUserName.setText(employee.getUserName());
            fldBirthDate.setValue(employee.getBirthDate());
            admin.setSelected(employee.isAdmin());
            fldHireDate.setValue(employee.getHireDate());
        }

    }

    public Employee getEmployee() { return employee; }

    public void clickCancel(ActionEvent actionEvent) {
        employee = null;
        Stage stage = (Stage) fldFirstName.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) throws ParseException {
        boolean sveOk = true;

        //validacija ime
        if (fldFirstName.getText().trim().isEmpty()) {
            fldFirstName.getStyleClass().removeAll("valid");
            fldFirstName.getStyleClass().add("nonvalid");
            sveOk = false;
        } else {
            fldFirstName.getStyleClass().removeAll("nonvalid");
            fldFirstName.getStyleClass().add("valid");
        }
        //validacija prezime
        if (fldLastName.getText().trim().isEmpty()) {
            fldLastName.getStyleClass().removeAll("valid");
            fldLastName.getStyleClass().add("nonvalid");
            sveOk = false;
        } else {
            fldLastName.getStyleClass().removeAll("nonvalid");
            fldLastName.getStyleClass().add("valid");
        }
        //validacija lozinka
        if (fldPassword.getText().trim().isEmpty()) {
            fldPassword.getStyleClass().removeAll("valid");
            fldPassword.getStyleClass().add("nonvalid");
            sveOk = false;
        } else {
            fldPassword.getStyleClass().removeAll("nonvalid");
            fldPassword.getStyleClass().add("valid");
        }

        //valiadcija koricnickoIme
        if(employeeDAO.isUserNameTaken(fldUserName.getText(), employee) == false || fldUserName.getText().isEmpty()) {
            if(employeeDAO.isUserNameTaken(fldUserName.getText(), employee) == false)
                takenUserName.setVisible(true);
            fldUserName.getStyleClass().removeAll("valid");
            fldUserName.getStyleClass().add("nonvalid");
            sveOk = false;
        }
        else {
            fldUserName.getStyleClass().removeAll("nonvalid");
            fldUserName.getStyleClass().add("valid");
        }
        //validacija ako employee nije null korisnickoIme


        //validacija datumrodjenja
        if(fldBirthDate.getValue() == null || Period.between(fldBirthDate.getValue(), LocalDate.now()).getYears() < 18) {
            fldBirthDate.getStyleClass().removeAll("valid");
            fldBirthDate.getStyleClass().add("nonvalid");
            sveOk = false;
        }
        else {
            fldBirthDate.getStyleClass().removeAll("nonvalid");
            fldBirthDate.getStyleClass().add("valid");
        }

        //validacija datumzaposlenja
        if(fldHireDate.getValue() == null || fldHireDate.getValue().isAfter(LocalDate.now())) {
            fldHireDate.getStyleClass().removeAll("valid");
            fldHireDate.getStyleClass().add("nonvalid");
            sveOk = false;
        }
        else {
            fldHireDate.getStyleClass().removeAll("nonvalid");
            fldHireDate.getStyleClass().add("valid");
        }


        if (!sveOk) return;

        if (employee == null) employee = new Employee();
        employee.setFirstName(fldFirstName.getText());
        employee.setLastName(fldLastName.getText());
        employee.setPassword(fldPassword.getText());
        employee.setUserName(fldUserName.getText());
        employee.setAdmin(admin.isSelected());


        employee.setBirthDate(fldBirthDate.getValue());
        employee.setHireDate(fldHireDate.getValue());

        Stage stage = (Stage) fldFirstName.getScene().getWindow();
        stage.close();
    }
}
