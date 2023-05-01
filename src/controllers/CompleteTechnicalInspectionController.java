package controllers;

import dao.TechnicalInspectionDAO;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.TechnicalInspection;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class CompleteTechnicalInspectionController {
    private BorderPane mainPane;
    public TechnicalInspectionDAO technicalInspectionDAO;
    public ImageView dimensions;
    public Label main;
    //odabir
    public ChoiceBox<String> choiceEngineType, choiceFuelType, choiceGearbox, choiceEngineTact;
    //dimenzije vehicles
    public TextField width, height, length;
    //mjesta
    public TextField placesToSit, placesToStand, placesToLieDown;
    //komentar
    public TextArea comment;
    //check ispravnost
    public CheckBox checkValid, checkInvalid;
    public Button btnInvalid, btnValid;
    public TextField price;
    public TechnicalInspection technicalInspection;

    public CompleteTechnicalInspectionController(TechnicalInspection selectedItem, BorderPane mainPane) {
        this.mainPane = mainPane;
        technicalInspectionDAO = new TechnicalInspectionDAO();
        technicalInspection = selectedItem;
    }

    @FXML
    public void initialize() {
        main.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        dimensions.setImage(new Image("/img/cardimensions.jpg"));
        dimensions.setFitHeight(180);
        dimensions.setFitWidth(230);
        //vrsta motora
        choiceEngineType.getItems().add("Otto");
        choiceEngineType.getItems().add("Diesel");
        choiceEngineType.getItems().add("Kombinovani pogon");
        choiceEngineType.setValue(choiceEngineType.getItems().get(0));

        //vrstaGoriva
        choiceFuelType.getItems().add("Benzin");
        choiceFuelType.getItems().add("Diesel");
        choiceFuelType.setValue(choiceFuelType.getItems().get(0));

        //vrstaMjenjaca
        choiceGearbox.getItems().add("Automatski");
        choiceGearbox.getItems().add("Ručni");
        choiceGearbox.setValue(choiceGearbox.getItems().get(0));

        //taktnostMotora
        choiceEngineTact.getItems().add("Dvotaktni");
        choiceEngineTact.getItems().add("Četverotaktni");
        choiceEngineTact.setValue(choiceEngineTact.getItems().get(0));

        //check
        btnValid.setVisible(false);
        btnInvalid.setVisible(true);
        checkInvalid.setSelected(true);
        checkValid.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    checkInvalid.setSelected(!new_val);
                    btnValid.setVisible(!checkInvalid.isSelected());
                    btnInvalid.setVisible(checkInvalid.isSelected());
                });
        checkInvalid.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    checkValid.setSelected(!new_val);
                    btnValid.setVisible(!checkInvalid.isSelected());
                    btnInvalid.setVisible(checkInvalid.isSelected());
        });

    }

    public void clickComplete(ActionEvent actionEvent) {
        boolean sveOK = true;
        //validacija dimenzija
        if (!width.getText().matches("[0-9]+") || Integer.parseInt(width.getText()) < 1000) {
            width.getStyleClass().removeAll("valid");
            width.getStyleClass().add("invalid");
            sveOK = false;
        }
        else {
            width.getStyleClass().removeAll("invalid");
            width.getStyleClass().add("valid");

        }
        if (!height.getText().matches("[0-9]+") || Integer.parseInt(height.getText()) < 1000) {
            height.getStyleClass().removeAll("valid");
            height.getStyleClass().add("invalid");
            sveOK = false;
        }
        else {
            height.getStyleClass().removeAll("invalid");
            height.getStyleClass().add("valid");
        }
        if (!length.getText().matches("[0-9]+") || Integer.parseInt(length.getText()) < 1000) {
            length.getStyleClass().removeAll("valid");
            length.getStyleClass().add("invalid");
            sveOK = false;
        }
        else {
            length.getStyleClass().removeAll("invalid");
            length.getStyleClass().add("valid");
        }
        //validacija mjesta
        if (!placesToSit.getText().matches("[0-9]+") || Integer.parseInt(placesToSit.getText()) < 1) {
            placesToSit.getStyleClass().removeAll("valid");
            placesToSit.getStyleClass().add("invalid");
            sveOK = false;
        }
        else {
            placesToSit.getStyleClass().removeAll("invalid");
            placesToSit.getStyleClass().add("valid");
        }
        if (!placesToLieDown.getText().matches("[0-9]+") || Integer.parseInt(placesToLieDown.getText()) < 0) {
            placesToLieDown.getStyleClass().removeAll("valid");
            placesToLieDown.getStyleClass().add("invalid");
            sveOK = false;
        }
        else {
            placesToLieDown.getStyleClass().removeAll("invalid");
            placesToLieDown.getStyleClass().add("valid");
        }
        if (!placesToStand.getText().matches("[0-9]+") || Integer.parseInt(placesToStand.getText()) < 1) {
            placesToStand.getStyleClass().removeAll("valid");
            placesToStand.getStyleClass().add("invalid");
            sveOK = false;
        }
        else {
            placesToStand.getStyleClass().removeAll("invalid");
            placesToStand.getStyleClass().add("valid");
        }
        //cijena
        if(price.getText().isEmpty() || Double.parseDouble(price.getText()) > 500 || Double.parseDouble(price.getText()) < 0) {
            price.getStyleClass().removeAll("valid");
            price.getStyleClass().add("invalid");
            sveOK = false;
        }
        else {
            price.getStyleClass().removeAll("invalid");
            price.getStyleClass().add("valid");
        }
        if(sveOK == false) return;

        //mijenjamo
        technicalInspection.setEngineType(choiceEngineType.getValue());
        technicalInspection.setEngineTact(choiceEngineTact.getValue());
        technicalInspection.setTypeOfFuel(choiceFuelType.getValue());
        technicalInspection.setTypeOfGearbox(choiceGearbox.getValue());

        technicalInspection.setHeight(Double.parseDouble(height.getText()));
        technicalInspection.setWidth(Double.parseDouble(width.getText()));
        technicalInspection.setLength(Double.parseDouble(length.getText()));

        technicalInspection.setPlacesToSit(Integer.parseInt(placesToSit.getText()));
        technicalInspection.setPlacesToStand(Integer.parseInt(placesToStand.getText()));
        technicalInspection.setPlacesToLieDown(Integer.parseInt(placesToLieDown.getText()));

        technicalInspection.setComment(comment.getText());
        technicalInspection.setValid(checkValid.isSelected());
        technicalInspection.setPrice(Double.parseDouble(price.getText()));

        technicalInspection.setStatusTehnickogPregleda("Kompletiran");
        technicalInspectionDAO.updateTI(technicalInspection);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(ResourceBundle.getBundle("CompleteTITranslation").getLocale().equals(new Locale("bs"))) {
            alert.setTitle("Kompletiranje tehničkog pregleda");
            alert.setHeaderText(null);
            alert.setContentText("Uspješno ste kompletirali pregled!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/img/mainicon.png"));
            stage.showAndWait();
        }
        else {
            alert.setTitle("Completion of technical inspection");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully completed the inspection!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/img/mainicon.png"));
            stage.showAndWait();
        }
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/technicalinspection.fxml"), ResourceBundle.getBundle("TechnicalInspectionTranslation"));
            TechnicalInspectionController technicalInspectionController = new TechnicalInspectionController(mainPane);
            loader.setController(technicalInspectionController);
            root = loader.load();
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.setMaximized(true);
            mainPane.setCenter(root);


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}
