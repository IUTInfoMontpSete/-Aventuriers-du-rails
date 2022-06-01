package fr.umontpellier.iut.vues;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 * <p>
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */

// TODO : Faire a la fin (Démarrage du Jeu)


public class VueChoixJoueurs extends Stage {


    private ObservableList<String> nomsJoueurs;

    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    private Button demarrerPartie;
    private Scene scene;
    private VBox vboxscene;

    private TextField Joueurs1;
    private TextField Joueurs2;
    private TextField Joueurs3;
    private TextField Joueurs4;

    private Label labelJoueur1;
    private Label labelJoueur2;
    private Label labelJoueur3;
    private Label labelJoueur4;

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs() {
        nomsJoueurs = FXCollections.observableArrayList();
        demarrerPartie = new Button("Démarrer la partie");
        vboxscene = new VBox();
        Joueurs1 = new TextField();
        Joueurs1.setPrefWidth(100);

        Joueurs2 = new TextField();
        Joueurs2.setPrefWidth(100);

        Joueurs3 = new TextField();
        Joueurs3.setMaxWidth(100);

        Joueurs4 = new TextField();
        Joueurs4.setPrefWidth(100);

        labelJoueur1 = new Label("Joueur 1 : ");
        labelJoueur2 = new Label("Joueur 2 : ");
        labelJoueur3 = new Label("Joueur 3 : ");
        labelJoueur4 = new Label("Joueur 4 : ");

        vboxscene.getChildren().add(Joueurs1);
        vboxscene.getChildren().add(Joueurs2);
        vboxscene.getChildren().add(Joueurs3);
        vboxscene.getChildren().add(Joueurs4);
        vboxscene.getChildren().add(labelJoueur1);
        vboxscene.getChildren().add(demarrerPartie);
        vboxscene.setStyle("-fx-background-color: #c4bd1f"); // voir la VBox

        scene = new Scene(vboxscene, 300, 300);


        demarrerPartie.setOnAction(event -> {
            List<String> noms = new ArrayList<>();
            if (!Joueurs1.getText().isEmpty()) {

                noms.add(Joueurs1.getText());
            }
            if (!Joueurs2.getText().isEmpty()) {
                noms.add(Joueurs2.getText());
            }
            if (!Joueurs3.getText().isEmpty()) {
                noms.add(Joueurs3.getText());
            }
            if (!Joueurs4.getText().isEmpty()) {
                noms.add(Joueurs4.getText());
            }
            nomsJoueurs.addAll(noms);
            hide();
        });
        setTitle("Choix des joueurs");
        setScene(scene);
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
        nomsJoueurs.addListener(quandLesNomsDesJoueursSontDefinis);

    }

    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
    protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange) {
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs(); i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            } else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        int nbJoueurs = 0;
        for (int i = 1; i <= 4; i++) {
            if (getJoueurParNumero(i) != null && !getJoueurParNumero(i).equals(""))
                nbJoueurs++;
        }
        return nbJoueurs;
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     *
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        TextField textField = (TextField) getScene().lookup("#joueur" + playerNumber);
        return textField.getText();
    }


}
