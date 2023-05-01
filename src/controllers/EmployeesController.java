package controllers;

import dao.EmployeeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Employee;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class EmployeesController {
    public ListView listEmployees;
    private ObservableList<Employee> list;
    private EmployeeDAO employeeDAO;
    public Label lmain;

    public EmployeesController() {
        employeeDAO = new EmployeeDAO();
        list = FXCollections.observableArrayList(employeeDAO.employees());

    }

    @FXML
    public void initialize() {
        lmain.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        listEmployees.setItems(list);

    }
    public void addEmployee(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employee.fxml"), ResourceBundle.getBundle("EmployeesTranslation"));
            EmployeeController employeeController = new EmployeeController(null);
            loader.setController(employeeController);
            root = loader.load();
            if(ResourceBundle.getBundle("EmployeesTranslation").getLocale().equals(new Locale("bs")))
                stage.setTitle("Uposlenik");
            else
                stage.setTitle("Employee");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.getIcons().add(new Image("/img/mainicon.png"));
            stage.show();

            stage.setOnHiding( event -> {
                Employee employee = employeeController.getEmployee();
                if (employee != null) {
                    employeeDAO.addEmployee(employee);
                    list.setAll(employeeDAO.employees());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(ActionEvent actionEvent) {
        Employee employee = (Employee) listEmployees.getSelectionModel().getSelectedItem();
        if (employee == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employee.fxml"), ResourceBundle.getBundle("EmployeesTranslation"));
            EmployeeController employeeController = new EmployeeController(employee);
            loader.setController(employeeController);
            root = loader.load();
            if(ResourceBundle.getBundle("EmployeesTranslation").getLocale().equals(new Locale("bs")))
                stage.setTitle("Uposlenik");
            else
                stage.setTitle("Employee");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.getIcons().add(new Image("/img/mainicon.png"));
            stage.show();

            stage.setOnHiding( event -> {
                Employee employee1 = employeeController.getEmployee();
                if (employee1 != null) {
                    // Ovdje ne smije doći do izuzetka, jer se prozor neće zatvoriti
                    try {
                        employeeDAO.updateEmployee(employee1);
                        list.setAll(employeeDAO.employees());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(ActionEvent actionEvent) {
        Employee employee = (Employee) listEmployees.getSelectionModel().getSelectedItem();
        if (employee == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if(ResourceBundle.getBundle("EmployeesTranslation").getLocale().equals(new Locale("bs"))) {
            alert.setTitle("Potvrda brisanja Uposlenika");
            alert.setHeaderText("Brisanje uposlenika " + employee.getFirstName() + " " + employee.getLastName());
            alert.setContentText("Da li ste sigurni da zelite obrisati uposlenog?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/img/mainicon.png"));
        }
        else {
            alert.setTitle("Confirmation of deletion of Employees");
            alert.setHeaderText("Deleting Employee " + employee.getFirstName() + " " + employee.getLastName());
            alert.setContentText("Are You sure about this?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/img/mainicon.png"));
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            employeeDAO.deleteEmployee(employee);
            list.setAll(employeeDAO.employees());
        }
    }
}
