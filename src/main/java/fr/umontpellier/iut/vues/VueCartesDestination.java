package fr.umontpellier.iut.vues;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VueCartesDestination extends Stage {



    /**
     * //TODO : A lire (Graphique voir VueDestination)
     * Fonctionnement de la vue : afficher les cartes du joueur courant (Destination)
     * Mais probleme de property binding a corrigé
     * Pour ajout du graphique des cartes a reflechir (Possible de le faire mais 1/1) LONGGGGGGG
     */



    private Scene scene;

    private final HBox ensembleCartes;
    private VBox textdutextHaut;
    private VBox plateau;
    //private VueDuJeu vueDuJeu;

    private Label texte;



    public VueCartesDestination() {

        setTitle("Carte Destination");
        //setResizable(false);
        setAlwaysOnTop(true);

        plateau = new VBox();

        this.ensembleCartes = new HBox();
        this.textdutextHaut = new VBox();
        this.texte = new Label("Clicker sur la carte a enlever");
        texte.setStyle("-fx-font-size: 20px;");
        texte.setUnderline(true);
        textdutextHaut.setStyle("-fx-alignment: center;");

        this.textdutextHaut.getChildren().add(texte);
        plateau.getChildren().addAll(textdutextHaut,ensembleCartes);

        scene = new Scene(plateau, 400, 350);
        setScene(scene);
    }

    public HBox setEnsembleCartes() {
        return ensembleCartes;
    }


    @Override
    public String toString() {
        return "VueCartesDestination{" + "ensembleCartes=" + ensembleCartes + '}';
    }
}
