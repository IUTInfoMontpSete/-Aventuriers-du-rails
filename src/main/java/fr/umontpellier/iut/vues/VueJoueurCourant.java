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
        getChildren().add(nomJoueur);
        getChildren().add(carteJoueurCourant);
        carteJoueurCourant.setStyle("-fx-background-color: RED;");
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


    public void creerBindings() {
        listenerJoueurChange = (observable, oldValue, newValue) -> {
            Label carteDuJC = new Label();
            Label Test = new Label();   //TODO : à supprimer

            Platform.runLater(() -> {
                nomJoueur.setText(newValue.getNom());

                carteDuJC.setText(newValue.getCartesWagon().toString());
                Test.setText(newValue.getDestinations().toString());


                carteJoueurCourant.getChildren().remove(trouveLabelCarte(oldValue));
                carteJoueurCourant.getChildren().add(carteDuJC);

                System.out.println(nomJoueur.getText() + " -> " + carteDuJC.getText() + " -> " + Test.getText()); //TODO : à supprimer
            });

        };
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(listenerJoueurChange);
    }
}

