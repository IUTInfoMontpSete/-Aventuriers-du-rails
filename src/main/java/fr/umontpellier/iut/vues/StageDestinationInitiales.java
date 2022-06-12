package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class StageDestinationInitiales extends Stage {

    private IJeu jeu;

    @FXML
    private RadioButton rb1, rb2, rb3, rb4;

    @FXML
    private AnchorPane anchorpane;


    private boolean fini = false;

    public StageDestinationInitiales() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/StageDestinationsInitiales.fxml"));
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
     * Choix sur la destination a défausser pour le joueur courant
     */
    public void destinationsInitialesBinding() {
        ListChangeListener<Destination> listenerDestinationsInitiales = new ListChangeListener<>() {

            @Override
            public void onChanged(Change<? extends Destination> change) {
                Platform.runLater(() -> {
                    try {
                        anchorpane.setStyle("-fx-border-color:"+VueDuJeu.getCouleurTraduction(jeu.joueurCourantProperty().get())+"; -fx-border-width: 5");
                        rb1.setText(change.getList().get(0).getNom());
                        rb2.setText(change.getList().get(1).getNom());
                        rb3.setText(change.getList().get(2).getNom());
                        rb4.setText(change.getList().get(3).getNom());
                        rb1.setSelected(false);
                        rb2.setSelected(false);
                        rb3.setSelected(false);
                        rb4.setSelected(false);
                    } catch (IndexOutOfBoundsException e) {
                        rb1.setText("");
                        rb2.setText("");
                        rb3.setText("");
                        rb4.setText("");
                        jeu.destinationsInitialesProperty().removeListener(this);
                        close();
                        fini = true;
                    }
                });
            }
        };

        jeu.destinationsInitialesProperty().addListener(listenerDestinationsInitiales);
    }

    /**
     * Validation du choix de la destination selectionnée
     */
    @FXML
    public void validerChoix() {
        try {
            if(rb1.isSelected()){
                jeu.destinationsInitialesProperty().remove(0);
            } else if(rb2.isSelected()){
                jeu.destinationsInitialesProperty().remove(1);
            } else if(rb3.isSelected()){
                jeu.destinationsInitialesProperty().remove(2);
            } else if(rb4.isSelected()){
                jeu.destinationsInitialesProperty().remove(3);
            }
        } catch (IndexOutOfBoundsException e) {
            close();
        }

        this.close();
        jeu.passerAEteChoisi();
    }

    public boolean getStatusFini() {
        return fini;
    }
}
