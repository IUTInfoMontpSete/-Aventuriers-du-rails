package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

    //private VuePlateau plateau;
    private  VueFenetre vueFenetre;

    private static ListChangeListener<Destination> listenersdestinations;
    private Button passer;
    private VBox listesdestinations;
    private VueJoueurCourant vueJoueurCourant;
    private VueCartesDestination vueCartesDestination;
    private Button show;
    private Button hide;
    private IJoueur joueurCourant;
    private HBox cartes;




    public VueDuJeu(IJeu jeu) {

        this.setPrefSize(700, 700);

        this.jeu = jeu;

        vueFenetre = new VueFenetre();
        getChildren().add(vueFenetre);
        //plateau = new VuePlateau();
        //getChildren().add(plateau); //  SI ON A UNE VUE PLATEAU

        this.passer = new Button("Passer");

        this.listesdestinations = new VBox();
        passer.setOnAction(e -> jeu.passerAEteChoisi());
        getChildren().add(passer);
        getChildren().add(listesdestinations);
        vueJoueurCourant = new VueJoueurCourant();
        getChildren().add(vueJoueurCourant);


        listesdestinations.setStyle("-fx-border-color: black;");
        setPrefSize(200, 400);

        this.cartes = new HBox();
        getChildren().add(cartes);

    }

    public HBox getCartes() {
        return cartes;
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
    }

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
                            cartes.getChildren().add(labelDestChoisi);

                        }
                    }
                    if (c.wasRemoved()) {
                        for (Destination i : c.getRemoved()) {
                            listesdestinations.getChildren().remove(trouveLabelDestination(i));
                        }
                    }
                    vueCartesDestination.setEnsembleCartes().getChildren().add(labelDestChoisi);// VueCartesDestination ajouté carte
                    //System.out.println(jeu.instructionProperty().toString() +" "+ jeu.getJoueurs()); // Debut de la partie
                }
            });
        };




        jeu.destinationsInitialesProperty().addListener(listenersdestinations);
        vueJoueurCourant.creerBindings();
    }

    @FXML
    public void initialize() {
        creerBindings();
    }

}
