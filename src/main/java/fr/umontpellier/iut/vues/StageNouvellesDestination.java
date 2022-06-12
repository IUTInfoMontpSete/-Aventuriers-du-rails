package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class StageNouvellesDestination extends Stage {

    private IJeu jeu;

    @FXML
    private CheckBox d1, d2, d3;

    @FXML
    private Label labelInfo;

    @FXML
    private AnchorPane ancorePaneND;

    public StageNouvellesDestination() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/StageNouvellesDestinations.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter pour recupérer le jeu
     * @param jeu
     */
    public void setJeu(IJeu jeu) {
        this.jeu = jeu;
    }

    /**
     * Choix sur la destination a rajouter pour le joueur courant
     */
    @FXML
    public void validerChoix() {
        if (d1.isSelected() && d2.isSelected() && d3.isSelected()) {
            labelInfo.setText("Vous ne pouvez pas choisir 3 destinations");
        } else {
            if (d1.isSelected()) {
                jeu.uneDestinationAEteChoisie(d1.getText());
            }
            if (d2.isSelected()) {
                jeu.uneDestinationAEteChoisie(d2.getText());
            }
            if (d3.isSelected()) {
                jeu.uneDestinationAEteChoisie(d3.getText());
            }
            close();
            jeu.passerAEteChoisi();
        }
    }

    @FXML
    public void validerChoixEnter(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER) {
            validerChoix();
        }
    }

    /**
     * Nouvelles destinations piocher
     */
    public void nouvellesDestinations(){
        jeu.uneDestinationAEtePiochee();
    }

    /**
     * Vue des nouvelles destinations piocher
     */
    public void nouvellesDestinationsBinding(){
        ListChangeListener<Destination> listenerNouvellesDestinations = new ListChangeListener<>() {

            @Override
            public void onChanged(Change<? extends Destination> change) {
                Platform.runLater(() -> {
                    try {
                        ancorePaneND.setStyle("-fx-border-color:"+VueDuJeu.getCouleurTraduction(jeu.joueurCourantProperty().get())+"; -fx-border-width: 5");
                        d1.setText(change.getList().get(0).getNom());
                        d2.setText(change.getList().get(1).getNom());
                        d3.setText(change.getList().get(2).getNom());

                        d1.setSelected(false);
                        d2.setSelected(false);
                        d3.setSelected(false);
                    } catch (IndexOutOfBoundsException e) {
                    }
                });
            }
        };

        jeu.destinationsInitialesProperty().addListener(listenerNouvellesDestinations);
    }
}