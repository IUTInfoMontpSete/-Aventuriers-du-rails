package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
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
    private Label nomJoueur;


    public VueJoueurCourant(IJoueur ijoueur) {
        this.joueurCourant = ijoueur;
    }


    public IJoueur getIJoueurCourant() {
        return joueurCourant;
    }

    public void creerBindings() {

        listenerJoueurChange = (ListChangeListener.Change<? extends Joueur> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (Joueur j : c.getAddedSubList()) {
                        if (j.equals(joueurCourant)) {
                            nomJoueur.setText(j.getNom());

                        }

                    }

                }
            }
        };
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener((ChangeListener<? super IJoueur>) listenerJoueurChange);
    }
}



    /*
    public void getAvatar(){

        if(ijoueur.getCouleur().equals("BLEU")){
            ImageView avatarBleu = new ImageView(new Image("/images/avatar-BLEU.png"));
        }
        if(ijoueur.getCouleur().equals("ROUGE")){
            ImageView avatarRouge = new ImageView(new Image("/images/avatar-ROUGE.png"));
        }
        if(ijoueur.getCouleur().equals("VERT")){
            ImageView avatarVert = new ImageView(new Image("/images/avatar-VERT.png"));
        }
        if(ijoueur.getCouleur().equals("JAUNE")){
            ImageView avatarJaune = new ImageView(new Image("/images/avatar-JAUNE.png"));
        }
        if(ijoueur.getCouleur().equals("ROSE")){
            ImageView avatarRose = new ImageView(new Image("/images/avatar-ROSE.png"));
        }
     */

