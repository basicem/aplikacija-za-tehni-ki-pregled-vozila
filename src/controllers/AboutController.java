package controllers;

import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class AboutController {

    public void webLink(ActionEvent actionEvent)  {
        try {
            Desktop.getDesktop().browse(new URI("https://c2.etf.unsa.ba/course/view.php?id=49"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void gitLink(ActionEvent activeEvent)  {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/RPR-2019/rpr20-projekat-bbbasic"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
