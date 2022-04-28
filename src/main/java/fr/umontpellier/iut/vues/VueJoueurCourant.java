package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Pane;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends Pane {


    private IJoueur ijoueur;    // TODO : La couleur choisi par le joueur courant designe sont AVATAR

    /**
     * Constructeur
     */
    public VueJoueurCourant(IJoueur ijoueur){
        this.ijoueur = ijoueur;
    }


    public IJoueur getIJoueur(){
        return this.ijoueur;
    }
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
}
