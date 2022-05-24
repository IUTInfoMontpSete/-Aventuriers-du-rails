package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Cette classe représente la vue d'une carte Wagon.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends VBox {

    private ICouleurWagon couleurWagon;
    private ChangeListener<IJoueur> carteChoisieParJC;
    private Label TestLabel;


    public VueCarteWagon(ICouleurWagon couleurWagon) {
        this.couleurWagon = couleurWagon;
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

    public void carteBidings(IJeu jeu) {
        this.carteChoisieParJC = (observable, oldValue, newValue) -> {
            // TODO : Ajouter la carte choisi par le joueur courant et la supprimer de la pioche
        };
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(carteChoisieParJC);
    }
}