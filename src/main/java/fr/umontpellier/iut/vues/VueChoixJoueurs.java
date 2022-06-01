package fr.umontpellier.iut.vues;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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

    private HBox hBoxJoueurs1;
    private HBox hBoxJoueurs2;
    private HBox hBoxJoueurs3;
    private HBox hBoxJoueurs4;
    private HBox hBoxButton;
    private HBox hBoxtextHaut;

    private TextField joueurs1;
    private TextField joueurs2;
    private TextField joueurs3;
    private TextField joueurs4;

    private Label labelJoueur1;
    private Label labelJoueur2;
    private Label labelJoueur3;
    private Label labelJoueur4;
    private Label textHaut;

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs(){
        nomsJoueurs = FXCollections.observableArrayList();

        hBoxtextHaut = new HBox();
        textHaut = new Label("Choisisez le nombre de joueurs  (Min : 2)");
        textHaut.setStyle("-fx-font-size: 15px;");
        hBoxtextHaut.getChildren().add(textHaut);
        hBoxtextHaut.setAlignment(Pos.CENTER);

       hBoxButton = new HBox();
        demarrerPartie = new Button("Démarrer la partie");
        hBoxButton.getChildren().add(demarrerPartie);
        hBoxButton.setAlignment(Pos.CENTER);

        vboxscene = new VBox();

        hBoxJoueurs1 = new HBox();
        hBoxJoueurs1.setAlignment(Pos.CENTER);
        hBoxJoueurs2 = new HBox();
        hBoxJoueurs2.setAlignment(Pos.CENTER);
        hBoxJoueurs3 = new HBox();
        hBoxJoueurs3.setAlignment(Pos.CENTER);
        hBoxJoueurs4 = new HBox();
        hBoxJoueurs4.setAlignment(Pos.CENTER);

        joueurs1 = new TextField();
        joueurs1.setPromptText("Obligatoire");
        joueurs1.setPrefWidth(100);

        joueurs2 = new TextField();
        joueurs2.setPromptText("Obligatoire");
        joueurs2.setPrefWidth(100);

        joueurs3 = new TextField();
        joueurs3.setPromptText("Facultatif");
        joueurs3.setMaxWidth(100);

        joueurs4 = new TextField();
        joueurs4.setPromptText("Facultatif");
        joueurs4.setPrefWidth(100);

        labelJoueur1 = new Label("Joueur 1 : ");
        labelJoueur2 = new Label("Joueur 2 : ");
        labelJoueur3 = new Label("Joueur 3 : ");
        labelJoueur4 = new Label("Joueur 4 : ");

        hBoxJoueurs1.getChildren().addAll(labelJoueur1, joueurs1);
        hBoxJoueurs2.getChildren().addAll(labelJoueur2, joueurs2);
        hBoxJoueurs3.getChildren().addAll(labelJoueur3, joueurs3);
        hBoxJoueurs4.getChildren().addAll(labelJoueur4, joueurs4);


        vboxscene.getChildren().addAll(hBoxtextHaut, hBoxJoueurs1, hBoxJoueurs2, hBoxJoueurs3, hBoxJoueurs4, hBoxButton);
        vboxscene.setStyle("-fx-background-color: #c4bd1f"); // voir la VBox

        scene = new Scene(vboxscene, 300, 300);


        demarrerPartie.setOnAction(event -> {
            List<String> noms = new ArrayList<>();
            if (!joueurs1.getText().isEmpty()) {

                noms.add(joueurs1.getText());
            }
            if (!joueurs2.getText().isEmpty()) {
                noms.add(joueurs2.getText());
            }
            if (!joueurs3.getText().isEmpty()) {
                noms.add(joueurs3.getText());
            }
            if (!joueurs4.getText().isEmpty()) {
                noms.add(joueurs4.getText());
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
