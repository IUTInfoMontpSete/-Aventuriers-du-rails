package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * <p>
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends VBox {

    private IJeu jeu;
    /**
     * private VuePlateau plateau;
     */
    private static ListChangeListener<Destination> listenersdestinations;
    private Button passer;
    private VBox listesdestinations;
    private VueJoueurCourant vueJoueurCourant;



    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        /** plateau = new VuePlateau(); */
        /** getChildren().add(plateau);*/
        this.passer = new Button("Passer");


        this.listesdestinations = new VBox();
        passer.setOnAction(e -> {
            jeu.passerAEteChoisi();
        });
        getChildren().add(passer);
        getChildren().add(listesdestinations);
        vueJoueurCourant = new VueJoueurCourant();
        getChildren().add(vueJoueurCourant);


        listesdestinations.setStyle("-fx-border-color: black;");

        setPrefSize(200, 400);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public Label trouveLabelDestination(IDestination i) {
        Label tld = new Label();
        for (Node node : listesdestinations.getChildren()) {
            tld = (Label) node;
            if (tld.equals(new Label(i.getNom()))) {
                break;
            }
        }
        return tld;
    } // Same in VueJoueurCourant

    public void creerBindings() {

        listenersdestinations = (ListChangeListener.Change<? extends Destination> c) -> {
            Platform.runLater(() -> {
                while (c.next()) {
                    Label labelDestChoisi = new Label();
                    labelDestChoisi.setPrefSize(200, 20);
                    if (c.wasAdded()) {
                        for (Destination i : c.getAddedSubList()) {
                            labelDestChoisi.setText(i.getNom());
                            listesdestinations.getChildren().add(labelDestChoisi);
                        }
                    }
                    if (c.wasRemoved()) {
                        for (Destination i : c.getRemoved()) {
                            listesdestinations.getChildren().remove(trouveLabelDestination(i));
                        }
                    }
                }
            });
        };
        jeu.destinationsInitialesProperty().addListener(listenersdestinations);
        vueJoueurCourant.creerBindings();

    }
}
