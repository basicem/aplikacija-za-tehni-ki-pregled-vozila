package controllers;

import dao.CustomerDAO;
import dao.TechnicalInspectionDAO;
import dao.VehicleDAO;
import enums.VehicleType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import models.Customer;

import javafx.event.ActionEvent;
import models.Employee;
import models.TechnicalInspection;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchController {
    public Label main, info;
    public CustomerDAO customerDAO;
    public VehicleDAO vehicleDAO;
    public TechnicalInspectionDAO technicalInspectionDAO;
    public ComboBox<VehicleType> choiceTypeVehicle;
    public ObservableList<VehicleType> vehicleType;
    public ComboBox<Customer> choiceCustomer;
    public ObservableList<Customer> customers;
    public Button btnSave;
    public DatePicker choiceDate;
    //tabelica
    public TableColumn colDateOfInspection;
    public TableColumn colCustomer;
    public TableColumn colVehicle;
    public TableColumn colStatusOfTechnicalInspection;
    public TableColumn colEmployees;

    public TableView<TechnicalInspection> tableView;


    @FXML
    public void initialize() {
        main.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08); -fx-border-width: 5;");
        info.setStyle("-fx-border-color: #a6d4fa;");
        choiceTypeVehicle.setItems(vehicleType);
        choiceCustomer.setItems(customers);
        choiceCustomer.getItems().add(null);
        choiceTypeVehicle.getItems().add(null);

        colDateOfInspection.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("dateOfInspection"));
        colVehicle.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("vehicle"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("customer"));
        colStatusOfTechnicalInspection.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("statusOfTechnicalInspection"));
        colEmployees.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, ArrayList<Employee>>("employees"));
        tableView.setItems(technicalInspectionDAO.search(null, null, null));

    }

    public SearchController(BorderPane gridPane) {
        customerDAO = new CustomerDAO();
        vehicleDAO = new VehicleDAO();
        technicalInspectionDAO = new TechnicalInspectionDAO();
        vehicleType = FXCollections.observableArrayList(Arrays.asList(VehicleType.values()));
        customers = FXCollections.observableArrayList(customerDAO.customers());
    }

    public void clickSearch(ActionEvent actionEvent) {
        tableView.setItems(technicalInspectionDAO.search(choiceCustomer.getValue(), choiceTypeVehicle.getValue(), choiceDate.getValue()));
        tableView.refresh();
        if(tableView.getItems().isEmpty())
            btnSave.setDisable(true);
        else
            btnSave.setDisable(false);

    }
    public void clickSave(ActionEvent actionEvent) {
        FileChooser izbornik  = new FileChooser();
        izbornik.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tekstualna", "*.txt"));
        File izabrani = izbornik.showOpenDialog(main.getScene().getWindow());
        technicalInspectionDAO.recordFile(izabrani, tableView.getItems());
    }

}
