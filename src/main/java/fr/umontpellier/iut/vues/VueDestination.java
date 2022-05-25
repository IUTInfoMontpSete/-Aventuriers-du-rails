package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import fr.umontpellier.iut.rails.Ville;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Cette classe représente la vue d'une carte Destination.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends Button {

    private IDestination destination;
    private VueDuJeu vueDuJeu;
    private Button carteDestinationButton;
    private Joueur joueur;
    private IJoueur nomJoueur;

    public VueDestination(IDestination destination) {
        this.destination = destination;
    }

    public IDestination getDestination() {
        return destination;
    }


    // TODO : clicker sur la carte que je ne veut pas dans mon jeux (Label Clickable)
    //TODO : set on action, image



    public void destinationBidings() {

        nomJoueur.getNom();
        joueur.getDestinations();
        Label label = new Label();
        label.setText("Choisissez la carte à enlever de votre main");
        label.setMinHeight(50);
        label.setMinWidth(50);
        label.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        label.setPadding(new Insets(20));

    }

}
