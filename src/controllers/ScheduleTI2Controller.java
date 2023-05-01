package controllers;

import dao.CustomerDAO;
import dao.TechnicalInspectionDAO;
import dao.TechnicalInspectionTeamDAO;
import dao.EmployeeDAO;
import enums.TypeOfTechnicalInspection;
import exceptions.WrongPhoneNumber;
import exceptions.ScheduledDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Customer;
import models.TechnicalInspection;
import models.Vehicle;
import services.UserSession;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;


public class ScheduleTI2Controller {
    public TechnicalInspectionDAO technicalInspectionDAO;
    public EmployeeDAO employeeDAO;
    public TechnicalInspectionTeamDAO technicalInspectionTeamDAO;
    public Label l2, l3, main;

    public TextField fldFirstName, fldLastName, fldAdress, fldPhoneNumber;
    public CustomerDAO customerDAO;
    public Label fldWrongPhoneNumber;
    public Vehicle vehicle;

    public ChoiceBox<TypeOfTechnicalInspection> choiceTypeOfTI;
    public ObservableList<TypeOfTechnicalInspection> typeOfTechnicalInspection;

    public DatePicker choiceDate;

    @FXML
    public void initialize() {
        fldWrongPhoneNumber.setVisible(false);
        l2.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        l3.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        main.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        choiceTypeOfTI.setItems(typeOfTechnicalInspection);
        choiceTypeOfTI.setValue(typeOfTechnicalInspection.get(0));
        choiceDate.setValue(LocalDate.now());

    }

    public ScheduleTI2Controller(Vehicle vehicle) {
        technicalInspectionDAO = new TechnicalInspectionDAO();
        employeeDAO = new EmployeeDAO();
        technicalInspectionTeamDAO = new TechnicalInspectionTeamDAO();
        this.vehicle = vehicle;
        customerDAO = new CustomerDAO();
        typeOfTechnicalInspection = FXCollections.observableArrayList(Arrays.asList(TypeOfTechnicalInspection.values()));

    }

    public void onOkClick(ActionEvent actionEvent) {
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

        //validacija telefona
        try {
            validationPhoneNumber(fldPhoneNumber.getText());
            fldPhoneNumber.getStyleClass().removeAll("nonvalid");
            fldPhoneNumber.getStyleClass().add("valid");
            fldWrongPhoneNumber.setVisible(false);

        } catch (WrongPhoneNumber wrongPhoneNumber) {
            fldPhoneNumber.getStyleClass().removeAll("valid");
            fldPhoneNumber.getStyleClass().add("nonvalid");
            fldWrongPhoneNumber.setVisible(true);
            sveOk = false;
            System.out.println(wrongPhoneNumber.getMessage());
        }

        //validacija prebivalista
        if (fldAdress.getText().trim().isEmpty()) {
            fldAdress.getStyleClass().removeAll("valid");
            fldAdress.getStyleClass().add("nonvalid");
            sveOk = false;
        } else {
            fldAdress.getStyleClass().removeAll("nonvalid");
            fldAdress.getStyleClass().add("valid");
        }

        //validacija datuma
        if (choiceDate.getValue() == null || choiceDate.getValue().isBefore(LocalDate.now())) {
            choiceDate.getStyleClass().removeAll("valid");
            choiceDate.getStyleClass().add("nonvalid");
            choiceTypeOfTI.getStyleClass().add("valid");
            sveOk = false;
        } else {
            choiceDate.getStyleClass().removeAll("nonvalid");
            choiceDate.getStyleClass().add("valid");
            choiceTypeOfTI.getStyleClass().add("valid");
        }



        if(sveOk != true) return;
        Customer klijent= new Customer();
        klijent.setFirstName(fldFirstName.getText());
        klijent.setLastName(fldLastName.getText());
        klijent.setAddress(fldAdress.getText());
        klijent.setPhoneNumber(fldPhoneNumber.getText());

        customerDAO.addCustomer(klijent);

        //pravimo tehnicki da ga dodamo jer je sve ok
        TechnicalInspection technicalInspection = new TechnicalInspection();

        technicalInspection.setStatusTehnickogPregleda("Zakazan");
//        //ovdje smo mijenjali
//        technicalInspection.setKlijent(klijent);
        technicalInspection.setVehicle(vehicle);
        technicalInspection.setCustomer(klijent);
        technicalInspection.setVrstaTehnickogPregleda(choiceTypeOfTI.getValue().toString());
        technicalInspection.setDateOfInspection(choiceDate.getValue());
        technicalInspection.getEmployees().add(employeeDAO.getEmployeeWithUserName(UserSession.getUserName()));

        try {
            technicalInspectionDAO.addTI(technicalInspection);
            if(ResourceBundle.getBundle("ScheduleTITranslation").getLocale().equals(new Locale("bs"))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tehnicki pregled");
                alert.setHeaderText("Uspjesno ste zakazali tehnicki pregled!");
                technicalInspectionTeamDAO.connectTIAndEmployee(technicalInspection.getId(), employeeDAO.getEmployeeWithUserName(UserSession.getUserName()).getId());
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tecnical Inspection");
                alert.setHeaderText("You have successfully scheduled a technical inspection!");
                technicalInspectionTeamDAO.connectTIAndEmployee(technicalInspection.getId(), employeeDAO.getEmployeeWithUserName(UserSession.getUserName()).getId());
                alert.showAndWait();

            }
        }catch (ScheduledDate scheduledDate) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tecnical Inspection");
            if(ResourceBundle.getBundle("ScheduleTITranslation").getLocale().equals(new Locale("eng")))
                alert.setHeaderText("Already scheduled!");
            else
                alert.setHeaderText("Zakazan termin!");
            alert.showAndWait();
        }
    }

    private boolean validationPhoneNumber(String fldBrojTelefona) throws WrongPhoneNumber {
        if(fldBrojTelefona.length() != 11) throw new WrongPhoneNumber("Duzina telefonskob broja treba biti 11");
        if(fldBrojTelefona.charAt(3) != '-' || fldBrojTelefona.charAt(7) != '-') throw new WrongPhoneNumber("Neispravan format telefonskog broja");
        for(int i = 0; i < fldBrojTelefona.length(); i++) {
            if(i == 3 || i == 7) continue;
            if(!Character.isDigit(fldBrojTelefona.charAt(i))) throw new WrongPhoneNumber("Neispravan format telefonskog broja");
        }
        return true;
    }
}
