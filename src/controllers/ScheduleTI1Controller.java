package controllers;

import constants.VehicleModel;
import dao.VehicleDAO;
import enums.VehicleBrand;
import exceptions.WrongVINNumber;
import exceptions.WrongRegistrationNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import enums.VehicleType;
import models.Vehicle;
import services.VIN;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ScheduleTI1Controller {
    public Label l1;
    private VehicleDAO vehicleDAO;
    public Vehicle vehicle;
    public CheckBox choiceWhite, choiceBlack, choiceBrown, choiceRed, choiceGray;

    public ChoiceBox<VehicleType> choiceVehicleType = new ChoiceBox<>();
    public  ObservableList<VehicleType> vehicleType;

    public ChoiceBox<VehicleBrand> choiceVehicleBrand = new ChoiceBox<>();
    public ObservableList<VehicleBrand> vehicleBrand;

    public ChoiceBox<String> choiceVehicleModel = new ChoiceBox<>();
    public ObservableList<String> vehicleModel;

    public TextField fldYearOfManufacture, fldRegistration;

    public TextField fldVINNumber;

    public CheckBox newVehicle;
    public ObservableList<Vehicle> vehicleObservableList;
    public ChoiceBox<Vehicle> choiceVehicle;

    public Label fldWrongRegistration, fldWrongVIN, noColorType, noColor, main;
    public RadioButton rbRegular, rbMetalic, rbFolio;

    public String color, colorType;
    public BorderPane mainPane;
    public GridPane gridNew, gridOld;



    @FXML
    public void initialize() {
        l1.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        main.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        choiceVehicleType.setItems(vehicleType);
        choiceVehicleBrand.setItems(vehicleBrand);
        //vidljivost
        gridNew.setDisable(true);
        gridOld.setDisable(false);
        choiceVehicleModel.setDisable(true);
        newVehicle.setSelected(false);
        fldWrongVIN.setVisible(false);
        fldWrongRegistration.setVisible(false);
        noColorType.setVisible(false);
        noColor.setVisible(false);
        actionNewVehicle(null);
        //lista vehicles vec unesenih
        choiceVehicle.setItems(vehicleObservableList);
        choiceVehicle.setValue(vehicleObservableList.get(0));

        choiceVehicleBrand.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            vehicleModel = FXCollections.observableArrayList(VehicleModel.listFromBrands.get(choiceVehicleBrand.getItems().get((Integer) number2)));
            choiceVehicleModel.setDisable(false);

            choiceVehicleModel.setItems(vehicleModel);
        });

    }

    public ScheduleTI1Controller(BorderPane mainPane) {
        this.mainPane = mainPane;
        vehicleDAO = new VehicleDAO();
        vehicleType =  FXCollections.observableArrayList(Arrays.asList(VehicleType.values()));
        vehicleBrand = FXCollections.observableArrayList(Arrays.asList(VehicleBrand.values()));
        vehicleObservableList = FXCollections.observableArrayList(vehicleDAO.vehicles());
    }

    public void clickCancel(ActionEvent actionEvent) {
        vehicle = null;
        Stage stage = (Stage) choiceWhite.getScene().getWindow();
        stage.close();
    }


    public void clickContinue(ActionEvent actionEvent){
        if(newVehicle.isSelected()) {
            boolean sveOk = true;

            //validacija tip
            if (choiceVehicleType.getValue() == null) {
                choiceVehicleType.getStyleClass().removeAll("valid");
                choiceVehicleType.getStyleClass().add("nonvalid");
                sveOk = false;
            } else {
                choiceVehicleType.getStyleClass().removeAll("nonvalid");
                choiceVehicleType.getStyleClass().add("valid");
            }

            //validacija marka
            if (choiceVehicleBrand.getValue() == null) {
                choiceVehicleBrand.getStyleClass().removeAll("valid");
                choiceVehicleBrand.getStyleClass().add("nonvalid");
                sveOk = false;
            } else {
                choiceVehicleBrand.getStyleClass().removeAll("nonvalid");
                choiceVehicleBrand.getStyleClass().add("valid");
            }

            //validacija model
            if (choiceVehicleModel.getValue() == null) {
                choiceVehicleModel.getStyleClass().removeAll("valid");
                choiceVehicleModel.getStyleClass().add("nonvalid");
                sveOk = false;
            } else {
                choiceVehicleModel.getStyleClass().removeAll("nonvalid");
                choiceVehicleModel.getStyleClass().add("valid");
            }

            //validacija godinaProizvodnje
            if (!validationYear(fldYearOfManufacture.getText())) {
                fldYearOfManufacture.getStyleClass().removeAll("valid");
                fldYearOfManufacture.getStyleClass().add("nonvalid");
                sveOk = false;
            } else {
                fldYearOfManufacture.getStyleClass().removeAll("nonvalid");
                fldYearOfManufacture.getStyleClass().add("valid");
            }
            //validacija tablica
            try {
                validationRegistration(fldRegistration.getText());
                vehicleDAO.isRegistrationTaken(fldRegistration.getText(), vehicle);
                fldRegistration.getStyleClass().removeAll("nonvalid");
                fldRegistration.getStyleClass().add("valid");
                fldWrongRegistration.setVisible(false);

            } catch (WrongRegistrationNumber wrongRegistrationNumber) {
                fldRegistration.getStyleClass().removeAll("valid");
                fldRegistration.getStyleClass().add("nonvalid");
                fldWrongRegistration.setVisible(true);
                sveOk = false;
                System.out.println(wrongRegistrationNumber.getMessage());
            }

            //validacija sasije
            try {
                VIN.isVinValid(fldVINNumber.getText());
                vehicleDAO.isChassisNumberTaken(fldVINNumber.getText(), vehicle);
                fldVINNumber.getStyleClass().removeAll("nonvalid");
                fldVINNumber.getStyleClass().add("valid");
                fldWrongVIN.setVisible(false);
            } catch (WrongVINNumber wrongVINNumber) {
                fldVINNumber.getStyleClass().removeAll("valid");
                fldVINNumber.getStyleClass().add("nonvalid");
                fldWrongVIN.setVisible(true);
                sveOk = false;
                System.out.println(wrongVINNumber.getMessage());
            }

            //validacija boje
            if (!choiceWhite.isSelected() && !choiceBlack.isSelected() && !choiceRed.isSelected() && !choiceBrown.isSelected() && !choiceGray.isSelected()) {
                noColor.setVisible(true);
                sveOk = false;
            } else {
                noColor.setVisible(false);
            }

            //validacija rb
            if (!rbRegular.isSelected() && !rbMetalic.isSelected() && !rbFolio.isSelected()) {
                noColorType.setVisible(true);
                sveOk = false;
            } else {
                noColorType.setVisible(false);
            }

            if (!sveOk) return;
            vehicle = new Vehicle();
            //POSTAVI SVE !!!!!!
            vehicle.setType(choiceVehicleType.getValue().toString());
            vehicle.setBrand(choiceVehicleBrand.getValue().toString());
            vehicle.setModel(choiceVehicleModel.getValue());
            vehicle.setYearOfProduction(Integer.parseInt(fldYearOfManufacture.getText()));
            vehicle.setRegistration(fldRegistration.getText());
            vehicle.setChassisNumber(fldVINNumber.getText());
            vehicle.setColor(color);
            vehicle.setColorType(colorType);
            vehicleDAO.addVehicle(vehicle);
        }
        else {
            vehicle = choiceVehicle.getValue();
        }
        //idemo dalje na klijenta i saljemo mu vozilo
            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scheduleTI2.fxml"), ResourceBundle.getBundle("ScheduleTITranslation"));
                ScheduleTI2Controller scheduleTI2Controller = new ScheduleTI2Controller(vehicle);
                loader.setController(scheduleTI2Controller);
                root = loader.load();
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(true);
                stage.setWidth(450);
                stage.setHeight(580);
                mainPane.setCenter(root);


            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    public void actionNewVehicle(ActionEvent actionEvent) {
        if(newVehicle.isSelected()) {
            gridOld.setDisable(true);
            gridNew.setDisable(false);

        }
        else {
            gridOld.setDisable(false);
            gridNew.setDisable(true);
        }

    }
    public void clickRed(ActionEvent actionEvent) {
        choiceBrown.setSelected(false);
        choiceBlack.setSelected(false);
        choiceWhite.setSelected(false);
        choiceGray.setSelected(false);
        color = "Crvena";

    }

    public void clickBlack(ActionEvent actionEvent) {
        choiceBrown.setSelected(false);
        choiceRed.setSelected(false);
        choiceWhite.setSelected(false);
        choiceGray.setSelected(false);
        color = "Crna";

    }

    public void clickGray(ActionEvent actionEvent) {
        choiceBrown.setSelected(false);
        choiceBlack.setSelected(false);
        choiceWhite.setSelected(false);
        choiceRed.setSelected(false);
        color = "Siva";

    }

    public void clickWhite(ActionEvent actionEvent) {
        choiceBrown.setSelected(false);
        choiceBlack.setSelected(false);
        choiceRed.setSelected(false);
        choiceGray.setSelected(false);
        color = "Bijela";

    }

    public void clickBrown(ActionEvent actionEvent) {
        choiceRed.setSelected(false);
        choiceBlack.setSelected(false);
        choiceWhite.setSelected(false);
        choiceGray.setSelected(false);
        color = "Smeđa";
    }


    public void clickRegular(ActionEvent actionEvent) {
        rbFolio.setSelected(false);
        rbMetalic.setSelected(false);
        colorType = "Obična";
    }

    public void clickMetalic(ActionEvent actionEvent) {
        rbFolio.setSelected(false);
        rbRegular.setSelected(false);
        colorType = "Metalik";
    }

    public void clickFolio(ActionEvent actionEvent) {
        rbRegular.setSelected(false);
        rbMetalic.setSelected(false);
        colorType = "Folija";
    }

    private boolean validationYear(String text) {
        if(text.isEmpty()) return false;
        int godina = Integer.parseInt(text);
        if(godina > LocalDate.now().getYear() || godina < 1908) return false;
        return true;
    }

    private boolean validationRegistration(String text) throws WrongRegistrationNumber {
        if(text.length() != 9) throw new WrongRegistrationNumber("Duzina Registracije treba biti 9");
        if(text.charAt(3) != '-' || text.charAt(5) != '-') throw new WrongRegistrationNumber("Neispravan format registracije");
        if(!Character.isDigit(text.charAt(1))  || !Character.isDigit(text.charAt(2))) throw new WrongRegistrationNumber("Neispravan format registracije");
        if(!Character.isDigit(text.charAt(6))  || !Character.isDigit(text.charAt(7)) || !Character.isDigit(text.charAt(7))) throw new WrongRegistrationNumber("Neispravan format registracije");
        String string = "AEJKMOT";
        if(string.indexOf(text.charAt(0)) == -1 || string.indexOf(text.charAt(4)) == -1) throw new WrongRegistrationNumber("Neispravan format registracije");

        return true;
    }

}
