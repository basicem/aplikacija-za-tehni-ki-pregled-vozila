import controllers.LogInController;
import dao.InitDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        InitDB initDB = new InitDB();
        try {
            initDB.delete();
        }
        catch (SQLException e) {

        }
        initDB.createDB();
        Locale.setDefault(new Locale("eng"));
        ResourceBundle bundle = ResourceBundle.getBundle("LogInTranslation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        LogInController ctrl = new LogInController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("MeCARnic");
        primaryStage.getIcons().add(new Image("/img/mainicon.png"));
        primaryStage.setScene(new Scene(root, 335, 169));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
