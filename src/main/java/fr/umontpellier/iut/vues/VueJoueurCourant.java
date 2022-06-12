package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collections;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {


    @FXML
    private Label nomJCourant, nbGaresJC, nbWagonsJC, cartesJC, missionsJC, missionsJC2;

    // Cartes de l'utilisateur
    @FXML
    private Label Blanc, Jaune, Rouge, Noir, Bleu, Vert, Orange, Rose, Locomotive;

    private IJeu jeu;

    public VueJoueurCourant() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueJoueurCourant.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter pour recupérer le jeu
     *
     * @param jeu
     */
    public void setJeu(IJeu jeu) {
        this.jeu = jeu;
    }

    /**
     * Informations sur le joueur courant (nom, nombre de gares, nombre de wagons, nombre de cartes destinations)
     */
    public void creerBindings() {

        ChangeListener<IJoueur> listenerJoueur = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur joueur, IJoueur t1) {
                Platform.runLater(() -> {
                    nomJCourant.setText(t1.getNom() + " - " + jeu.joueurCourantProperty().get().getScore() + " Pts");
                    nbGaresJC.setText(String.valueOf(t1.getNbGares()));
                    nbWagonsJC.setText(String.valueOf(t1.getNbWagons()));
                    setStyle("-fx-background-color: " + VueDuJeu.getCouleurTraduction(jeu.joueurCourantProperty().get()) + "; -fx-background-radius: 5;");

                    String missions = "";
                    for (int i = 0; i < 4; i++) {
                        try{
                        missions += jeu.joueurCourantProperty().get().getDestinations().get(i).getNom() + "\n";
                        }catch(IndexOutOfBoundsException e){
                            missions += "";
                        }
                    }
                    missionsJC.setText(missions);

                    missions = "";
                    for (int i = 4; i < jeu.joueurCourantProperty().get().getDestinations().size(); i++) {
                        try{
                        missions += jeu.joueurCourantProperty().get().getDestinations().get(i).getNom() + "\n";
                        }catch(IndexOutOfBoundsException e){
                            missions += "";
                        }
                    }
                    missionsJC2.setText(missions);

                    Collections.shuffle(jeu.joueurCourantProperty().get().cartesWagonProperty());
                });
            }
        };

        jeu.joueurCourantProperty().addListener(listenerJoueur);
    }


    /**
     * Nombre de carte wagon du joueur courant
     */
    public void bindingCartesJoueur() {
        ListChangeListener<CouleurWagon> cartesJoueur = new ListChangeListener<CouleurWagon>() {
            @Override
            public void onChanged(Change<? extends CouleurWagon> change) {
                Platform.runLater(() -> {
                    //Blanc, Jaune, Rouge, Noir, Bleu, Vert, Orange, Rose, Locomotive;
                    Blanc.setText("x" + Collections.frequency(change.getList(), CouleurWagon.BLANC));
                    Jaune.setText("x" + Collections.frequency(change.getList(), CouleurWagon.JAUNE));
                    Rouge.setText("x" + Collections.frequency(change.getList(), CouleurWagon.ROUGE));
                    Noir.setText("x" + Collections.frequency(change.getList(), CouleurWagon.NOIR));
                    Bleu.setText("x" + Collections.frequency(change.getList(), CouleurWagon.BLEU));
                    Vert.setText("x" + Collections.frequency(change.getList(), CouleurWagon.VERT));
                    Orange.setText("x" + Collections.frequency(change.getList(), CouleurWagon.ORANGE));
                    Rose.setText("x" + Collections.frequency(change.getList(), CouleurWagon.ROSE));
                    Locomotive.setText("x" + Collections.frequency(change.getList(), CouleurWagon.LOCOMOTIVE));
                });
            }
        };

        for (int i = 0; i < jeu.getJoueurs().size(); i++) {
            jeu.getJoueurs().get(i).cartesWagonProperty().addListener(cartesJoueur);
        }
    }

    /**
     * Récuperer l'endroit clicker sur le plateau de jeu
     *
     * @param event
     */
    public void choixCarte(MouseEvent event) {
        String carte = ((Node) event.getSource()).getId();
        jeu.uneCarteWagonAEteChoisie(CouleurWagon.valueOf(carte));
    }
}



