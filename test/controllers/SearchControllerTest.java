package controllers;

import dao.InitDB;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import models.TechnicalInspection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class SearchControllerTest {
    Stage theStage;

    @Start
    public void start(Stage stage) throws Exception {
        InitDB initDB = new InitDB();
        try {
            initDB.delete();
        } catch (SQLException e) {

        }
        initDB.createDB();
        Locale.setDefault(new Locale("eng"));
        ResourceBundle bundle = ResourceBundle.getBundle("LogInTranslation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        LogInController ctrl = new LogInController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("MeCARnic");
        stage.getIcons().add(new Image("/img/mainicon.png"));
        stage.setScene(new Scene(root, 335, 169));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }
    @BeforeEach
    void vratiNaDefault(FxRobot robot) {
        InitDB initDB = new InitDB();
        try {
            initDB.delete();
        } catch (SQLException e) {
        }
        initDB.createDB();
        robot.clickOn("#fldUserName");
        robot.write("mehomehic");

        robot.clickOn("#fldPassword");
        robot.write("lozinka");

        robot.clickOn("#btnLogIn");
    }

    @Test
    void testSearch(FxRobot robot) {

        robot.clickOn("#btnSearch");


        robot.clickOn("#choiceTypeVehicle").clickOn("Autobus");
        robot.clickOn("#btnSearch2");

        TableView tableView1 = robot.lookup("#tableView").queryAs(TableView.class);
        ObservableList<TechnicalInspection> technicalInspections1 = tableView1.getItems();
        assertEquals(1, technicalInspections1.size());

        robot.clickOn("#choiceCustomer").clickOn("Huso Husić");
        robot.clickOn("#btnSearch2");

        TableView tableView = robot.lookup("#tableView").queryAs(TableView.class);
        ObservableList<TechnicalInspection> technicalInspections = tableView.getItems();
        assertEquals(0, technicalInspections.size());


    }

}
