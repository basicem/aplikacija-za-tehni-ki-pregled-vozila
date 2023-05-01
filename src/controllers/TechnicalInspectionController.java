package controllers;

import dao.EmployeeDAO;
import dao.TechnicalInspectionDAO;
import dao.TechnicalInspectionTeamDAO;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Employee;
import models.TechnicalInspection;
import services.UserSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class TechnicalInspectionController {
    public EmployeeDAO employeeDAO;
    public ObservableList<Employee> employees;
    public TechnicalInspectionDAO technicalInspectionDAO;
    public TechnicalInspectionTeamDAO technicalInspectionTeamDAO;
    public Label labela;
    private ObservableList<PieChart.Data> pieChartData;
    public PieChart chart;
    //tabelica
    public TableColumn colDateOfInspection;
    public TableColumn colVehicle;
    public TableColumn colTypeOfTechnicalInspection;
    public TableColumn colStatusOfTechnicalInspection;
    public TableColumn colEmployees;
    public TableView<TechnicalInspection> tableView;
    //dugmadi
    public Button btnCancelTI;
    public Button btnAddEmployee;
    public ChoiceBox<Employee> choiceEmployee;
    public Button btnFinishTI;
    public BorderPane mainPane;

    @FXML
    public void initialize() {
        if(UserSession.getPrivileges()) {
            btnAddEmployee.setVisible(true);
            choiceEmployee.setVisible(true);
        }
        else {
            btnAddEmployee.setVisible(false);
            choiceEmployee.setVisible(false);
        }

        choiceEmployee.setItems(employees);
        labela.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        //tabelica
        colDateOfInspection.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("dateOfInspection"));
        colVehicle.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("vehicle"));
        colTypeOfTechnicalInspection.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("typeOfTechnicalInspection"));
        colStatusOfTechnicalInspection.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("statusOfTechnicalInspection"));
        colEmployees.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, ArrayList<Employee>>("employees"));

        tableView.setItems(technicalInspectionDAO.search(null,null,null));
        btnCancelTI.setDisable(true);
        btnAddEmployee.setDisable(true);
        choiceEmployee.setDisable(true);
        btnFinishTI.setDisable(true);
        refresh(chart);
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(tableView.getSelectionModel().isEmpty()) return;
                //brisanje
                if(tableView.getSelectionModel().getSelectedItem().getStatusOfTechnicalInspection() == "Zakazan" ||
                        (tableView.getSelectionModel().getSelectedItem().getDateOfInspection().isEqual(LocalDate.now()))) {
                        btnCancelTI.setDisable(false);
                    if(tableView.getSelectionModel().getSelectedItem().getStatusOfTechnicalInspection().equals("Zakazan"))
                        btnFinishTI.setDisable(false);
                }
                else {
                    btnCancelTI.setDisable(true);
                    btnFinishTI.setDisable(true);
                }
                choiceEmployee.setDisable(false);
            }
        });

        choiceEmployee.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                btnAddEmployee.setDisable(false);
            }
        });


    }
    public TechnicalInspectionController(BorderPane mainPane) {
        this.mainPane = mainPane;
        technicalInspectionDAO = new TechnicalInspectionDAO();
        employeeDAO = new EmployeeDAO();
        technicalInspectionTeamDAO = new TechnicalInspectionTeamDAO();
        employees = FXCollections.observableArrayList(employeeDAO.employees());
    }

    public void cancelTI(ActionEvent actionEvent) {
            technicalInspectionDAO.cancelTI(tableView.getSelectionModel().getSelectedItem());
            tableView.setItems(technicalInspectionDAO.search(null,null,null));
            tableView.refresh();
            refresh(chart);


    }

    public void addEmployee(ActionEvent actionEvent) {
        if(tableView.getSelectionModel().getSelectedItem().getEmployees().contains(choiceEmployee.getValue())) return;
        technicalInspectionTeamDAO.connectTIAndEmployee(tableView.getSelectionModel().getSelectedItem().getId(), choiceEmployee.getValue().getId());
        tableView.setItems(technicalInspectionDAO.search(null,null,null));
        tableView.refresh();
    }

    public void finishTI(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/completeTI.fxml"), ResourceBundle.getBundle("CompleteTITranslation"));
            CompleteTechnicalInspectionController completeTechnicalInspectionController = new CompleteTechnicalInspectionController(tableView.getSelectionModel().getSelectedItem(), mainPane);
            loader.setController(completeTechnicalInspectionController);
            root = loader.load();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.setWidth(1010);
            stage.setHeight(580);
            //MIJENJANJE
            mainPane.setCenter(root);
            return;

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void refresh(PieChart chart) {
        pieChartData  = FXCollections.observableArrayList(
                new PieChart.Data("Zakazani", technicalInspectionTeamDAO.countScheduledTI(employeeDAO.getEmployeeWithUserName(UserSession.getUserName()).getId())),
                new PieChart.Data("Otkazani", technicalInspectionTeamDAO.countCanceledTI(employeeDAO.getEmployeeWithUserName(UserSession.getUserName()).getId())),
                new PieChart.Data("Kompletirani", technicalInspectionTeamDAO.countcompletedTI(employeeDAO.getEmployeeWithUserName(UserSession.getUserName()).getId())));
        chart.setData(pieChartData);
        chart.setLegendVisible(false);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty()
                        )
                )
        );
    }
}
