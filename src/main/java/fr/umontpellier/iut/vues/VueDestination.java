package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends Pane {

    private IDestination destination;
    private ChangeListener<IDestination> destinationChoisieParJC;

    public VueDestination(IDestination destination) {
        this.destination = destination;
    }

    public IDestination getDestination() {
        return destination;
    }


    // TODO : Idem Vue Carte Wagon

    public void destinationBidings(){
        destinationChoisieParJC = (observable, oldValue, newValue) -> {
            throw new RuntimeException("Vous ne devriez pas avoir appelé cette méthode");
        };
    }

}
