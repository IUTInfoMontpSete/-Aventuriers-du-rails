package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.scene.layout.Pane;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * <p>
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends Pane {

    private IJeu jeu;
    private VuePlateau plateau;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();

        //autresJoueurs = new VueAutresJoueurs();
        //carteWagon = new VueCarteWagon();
        //choixJoueurs = new VueChoixJoueurs();
        joueurCourant = new VueJoueurCourant(joueurCourant.getIJoueur());
        //choixDestination = new VueDestination();



        getChildren().add(plateau);
        getChildren().add(joueurCourant);
        //getChildren().addAll(plateau, autresJoueurs, carteWagon, choixJoueurs, joueurCourant, choixDestination);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
    }

}