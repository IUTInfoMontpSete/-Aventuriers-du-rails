package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {


    private IJoueur joueurCourant;// TODO : La couleur choisi par le joueur courant designe sont AVATAR
    private static ListChangeListener<Joueur> listenerJoueurChange;
    private VBox carteJoueurCourant;

    public VueJoueurCourant() {
        this.nomJoueur = new Label();
        this.carteJoueurCourant = new VBox();
        getChildren().add(nomJoueur);
        getChildren().add(carteJoueurCourant);
        carteJoueurCourant.setStyle("-fx-background-color: RED;");
    }

    public void carteJoueurCourant(){


    }



    public IJoueur getIJoueurCourant() {
        return joueurCourant;
    }

    public void creerBindings() {
        listenerJoueurChange = (observable, oldValue, newValue) -> {
            Platform.runLater(() -> nomJoueur.setText(newValue.getNom()));
        };
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(listenerJoueurChange);
    }
}

