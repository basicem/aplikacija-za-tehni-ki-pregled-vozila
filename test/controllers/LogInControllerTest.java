package controllers;

import dao.InitDB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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

public class LogInControllerTest {
    Stage theStage;

    public static boolean sadrziStil(TextField polje, String stil) {
        for (String s : polje.getStyleClass())
            if (s.equals(stil)) return true;
        return false;
    }

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
    }

    @Test
    void testEmpty(FxRobot robot) {
        robot.clickOn("#btnLogIn");

        TextField polje1 = robot.lookup("#fldUserName").queryAs(TextField.class);
        assertTrue(sadrziStil(polje1, "fieldIncorrect"));

        TextField polje2 = robot.lookup("#fldPassword").queryAs(TextField.class);
        assertTrue(sadrziStil(polje2, "fieldIncorrect"));
    }

    @Test
    void testWrongUserName(FxRobot robot) {
        robot.clickOn("#fldUserName");
        robot.write("nikonikic");

        robot.clickOn("#fldPassword");
        robot.write("nikonikic");

        robot.clickOn("#btnLogIn");
        Label label =robot.lookup("#fldError").queryAs(Label.class);
        assertTrue(label.isVisible());

    }

    @Test
    void testOKUser(FxRobot robot) {
        robot.clickOn("#fldUserName");
        robot.write("mehomehic");

        robot.clickOn("#fldPassword");
        robot.write("lozinka");

        robot.clickOn("#btnLogIn");

        assertEquals("MeCARnic", theStage.getTitle());

    }
}