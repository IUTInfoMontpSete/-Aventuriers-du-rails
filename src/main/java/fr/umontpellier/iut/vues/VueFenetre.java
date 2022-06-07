package fr.umontpellier.iut.vues;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class VueFenetre extends Pane {

    public VueFenetre() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueDuJeu.fxml"));
            loader.setRoot(this);
            loader.load();
            loader.setController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
