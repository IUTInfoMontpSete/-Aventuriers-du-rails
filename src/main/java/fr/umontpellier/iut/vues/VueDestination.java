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
    private Button carteDestinationButton;
    private Joueur joueur;

    public VueDestination(IDestination destination) {
        this.destination = destination;
    }

    public IDestination getDestination() {
        return destination;
    }


    // TODO : clicker sur la carte que je ne veut pas dans mon jeux (Label Clickable)
    //TODO : set on action, image


    public void destinationBidings() {

        //for pour tout les destinations
        for (int i = 0; i < 4; i++) {
            carteDestinationButton = new Button();
            carteDestinationButton.setGraphic(new Label(destination.getNom()));
            carteDestinationButton.setPadding(new Insets(10, 10, 10, 10));
            carteDestinationButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 1px;");
            carteDestinationButton.setText("Choisir cette destination");
            carteDestinationButton.setOnAction(event -> joueur.getDestinations().remove(destination));
        }

    }
}
