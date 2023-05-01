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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class EmployeesControllerTest {
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
        ResourceBundle bundle = ResourceBundle.getBundle("EmployeesTranslation");
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employees.fxml"), ResourceBundle.getBundle("EmployeesTranslation"));
        EmployeesController administratorGlavniController = new EmployeesController();
        loader.setController(administratorGlavniController);
        root = loader.load();
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(true);
        stage.show();
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
    void updateEmployee(FxRobot robot) {

        robot.clickOn("Meho Mehić");
        robot.clickOn("#btnEditEmployee");
        robot.clickOn("#fldFirstName");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("Ime");
        robot.clickOn("#btnOk");

        ListView lista = robot.lookup("#listEmployees").queryAs(ListView.class);
        ObservableList<Employee> employees = lista.getItems();
        assertEquals(5, employees.size());

        assertEquals("Ime Mehić", employees.get(0).toString());
    }

    @Test
    void deleteEmployee(FxRobot robot) {

        robot.clickOn("Lana Lanić");
        robot.clickOn("#btnDeleteEmployee");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        ListView lista = robot.lookup("#listEmployees").queryAs(ListView.class);
        ObservableList<Employee> employees = lista.getItems();
        assertEquals(4, employees.size());

    }
}
