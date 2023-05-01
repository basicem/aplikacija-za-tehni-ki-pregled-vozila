package controllers;

import dao.EmployeeDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Employee;
import services.UserSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LogInController {
    public TextField fldUserName;
    public PasswordField fldPassword;
    public Label fldError;

    private EmployeeDAO employeeDAO;

    @FXML
    public void initialize() {
        fldError.setVisible(false);
        //fldUserName.setText("mehomehic");
        //fldPassword.setText("lozinka");
    }


    public LogInController() {
        employeeDAO = new EmployeeDAO();
    }

    public void clickLogIn(ActionEvent actionEvent) {
        if(fldPassword.getText().isEmpty() || fldUserName.getText().isEmpty()) {
            if(fldPassword.getText().isEmpty()) {
                fldPassword.getStyleClass().removeAll("fieldCorrect");
                fldPassword.getStyleClass().add("fieldIncorrect");
            }
            if(fldUserName.getText().isEmpty()) {
                fldUserName.getStyleClass().removeAll("fieldCorrect");
                fldUserName.getStyleClass().add("fieldIncorrect");
            }
            return;
        }

        ArrayList<Employee> pomocni = employeeDAO.employees();
        for(int i = 0; i < pomocni.size(); i++) {
            if(pomocni.get(i).getUserName().equals(fldUserName.getText()) && pomocni.get(i).getPassword().equals(fldPassword.getText())) {
                fldPassword.getStyleClass().removeAll("fieldIncorrect");
                fldPassword.getStyleClass().add("fieldCorrect");

                fldUserName.getStyleClass().removeAll("fieldIncorrect");
                fldUserName.getStyleClass().add("fieldCorrect");

                UserSession.getInstace(pomocni.get(i).getUserName(), pomocni.get(i).isAdmin());
                closeWindow();
            }
            else {
                fldError.setVisible(true);
                fldPassword.getStyleClass().removeAll("fieldCorrect");
                fldPassword.getStyleClass().add("fieldIncorrect");

                fldUserName.getStyleClass().removeAll("fieldCorrect");
                fldUserName.getStyleClass().add("fieldIncorrect");
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) fldPassword.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"), ResourceBundle.getBundle("HomeTranslation"));
            HomeController ctrl = new HomeController();
            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();
            stage.setTitle("MeCARnic");
            stage.setScene(new Scene(root, 396, 311));
            stage.setWidth(1200);
            stage.setHeight(730);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

