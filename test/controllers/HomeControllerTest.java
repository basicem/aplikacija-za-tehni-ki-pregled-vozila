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
import models.Employee;
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

@ExtendWith(ApplicationExtension.class)
public class HomeControllerTest {
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

    // Prije svakog testa vraćamo bazu na default stanje
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
    void addEmployeeToTI(FxRobot robot) {

        robot.clickOn("[Meho Mehić]");

        robot.clickOn("#choiceEmployee");
        robot.clickOn("Lana Lanić");
        robot.clickOn("#btnAddEmployee");

        TableView tableView = robot.lookup("#tableView").queryAs(TableView.class);
        ObservableList<TechnicalInspection> technicalInspections = tableView.getItems();
        assertEquals("[Meho Mehić, Lana Lanić]", technicalInspections.get(0).getEmployees().toString());
    }


    @Test
    void cancelTI(FxRobot robot) {
        robot.clickOn("Zakazan");

        robot.clickOn("#btnCancelTI");

        TableView tableView = robot.lookup("#tableView").queryAs(TableView.class);
        ObservableList<TechnicalInspection> technicalInspections = tableView.getItems();
        assertEquals("Otkazan", technicalInspections.get(0).getStatusOfTechnicalInspection());
    }

    @Test
    void finishTI(FxRobot robot) {
        robot.clickOn("Zakazan");

        robot.clickOn("#btnFinishTI");

        robot.clickOn("#width").write("2000");
        robot.clickOn("#length").write("2000");
        robot.clickOn("#height").write("2000");

        robot.clickOn("#placesToSit").write("2");
        robot.clickOn("#placesToStand").write("2");
        robot.clickOn("#placesToLieDown").write("2");

        robot.clickOn("#price").write("200.00");
        robot.clickOn("#btnComplete");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        TableView tableView = robot.lookup("#tableView").queryAs(TableView.class);
        ObservableList<TechnicalInspection> technicalInspections = tableView.getItems();
        assertEquals("Kompletiran", technicalInspections.get(0).getStatusOfTechnicalInspection());
    }

}
