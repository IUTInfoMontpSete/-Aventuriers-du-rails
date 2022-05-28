package fr.umontpellier.iut.vues;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class VueCartesDestination extends Stage {

    // TODO : Ici s'ajoutera les carte du listener 1 par 1 depuis l'autre class dans un HBox.

    private final HBox ensembleCartes;
    private VueDuJeu vueDuJeu;


    public VueCartesDestination() {
        this.ensembleCartes = new HBox(vueDuJeu.getCartes());
    }


    public HBox getEnsembleCartes() {
        return ensembleCartes;
    }

    @Override
    public String toString() {
        return "VueCartesDestination{" + "ensembleCartes=" + ensembleCartes + '}';
    }
}
