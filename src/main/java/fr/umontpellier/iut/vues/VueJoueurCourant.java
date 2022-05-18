package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
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
        this.destinationJoueurCourant = new VBox(); // TODO : ADD

        getChildren().add(nomJoueur);
        getChildren().add(carteJoueurCourant);
        getChildren().add(destinationJoueurCourant); // TODO : ADD

        carteJoueurCourant.setStyle("-fx-background-color: RED;");
        destinationJoueurCourant.setStyle("-fx-background-color: GREEN;"); // TODO : ADD
    }

    public Label trouveLabelCarte(IJoueur i) {
        Label tlc = new Label();
        for (Node node : carteJoueurCourant.getChildren()) {
            tlc = (Label) node;
            if (tlc.equals(new Label(i.getNom()))) {
                break;
            }
        }
        return tlc;
    }

    public Label trouveLabelDest(IJoueur i) {
        Label tlc = new Label();
        for (Node node : destinationJoueurCourant.getChildren()) {
            tlc = (Label) node;
            if (tlc.equals(new Label(i.getNom()))) {
                break;
            }
        }
        return tlc;
    }


    public void creerBindings() {
        listenerJoueurChange = (observable, oldValue, newValue) -> {
            Label carteDuJC = new Label();
            Label destinationDuJC = new Label();

            Platform.runLater(() -> {
                nomJoueur.setText(newValue.getNom());

                carteDuJC.setText(newValue.getCartesWagon().toString());
                destinationDuJC.setText(newValue.getDestinations().toString());


                carteJoueurCourant.getChildren().remove(trouveLabelCarte(oldValue)); // supprime l'ancienne carte du joueur courant
                carteJoueurCourant.getChildren().add(carteDuJC); // ajoute la nouvelle carte du joueur courant

                destinationJoueurCourant.getChildren().remove(trouveLabelDest(oldValue)); // supprime l'ancienne destination du joueur courant // TODO : ADD
                destinationJoueurCourant.getChildren().add(destinationDuJC); //ajoute la nouvelle destination du joueur courant // TODO : ADD

                System.out.println(nomJoueur.getText() + " -> " + carteDuJC.getText() + " -> " + destinationDuJC.getText()); //TODO : à supprimer
            });

        };
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(listenerJoueurChange);
    }
}

