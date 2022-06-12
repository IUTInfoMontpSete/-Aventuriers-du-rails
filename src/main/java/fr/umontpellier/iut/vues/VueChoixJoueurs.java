package fr.umontpellier.iut.vues;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

// TODO : Fini mais peu étre améliorée


public class VueChoixJoueurs extends Stage {

    private ObservableList<String> nomsJoueurs;

    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    private Button demarrerPartie;
    private Scene scene;
    private VBox vboxscene;
    private HBox hBoxJoueurs1, hBoxJoueurs2, hBoxJoueurs3, hBoxJoueurs4, hBoxButton, hBoxtextHaut;
    private TextField joueurs1, joueurs2, joueurs3, joueurs4;
    private Label labelJoueur1, labelJoueur2, labelJoueur3, labelJoueur4, textHaut;

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs() {

        nomsJoueurs = FXCollections.observableArrayList();

        //Label du haut de la fenêtre

        hBoxtextHaut = new HBox();
        textHaut = new Label("Choisisez le nombre de joueurs  (Min : 2)");
        textHaut.setUnderline(true);
        textHaut.setStyle("-fx-font-size: 18px;");
        hBoxtextHaut.getChildren().add(textHaut);
        hBoxtextHaut.setAlignment(Pos.CENTER);
        hBoxtextHaut.paddingProperty().setValue(new javafx.geometry.Insets(4, 10, 10, 10));

        //Button pour démarrer la partie

        hBoxButton = new HBox();
        demarrerPartie = new Button("Démarrer la partie");
        demarrerPartie.setStyle("-fx-font-size: 15px;");
        hBoxButton.getChildren().add(demarrerPartie);
        hBoxButton.setAlignment(Pos.CENTER);
        hBoxButton.paddingProperty().setValue(new javafx.geometry.Insets(10, 10, 10, 10));

        //HBox pour les joueurs

        hBoxJoueurs1 = new HBox();
        hBoxJoueurs1.setAlignment(Pos.CENTER);
        hBoxJoueurs1.paddingProperty().setValue(new javafx.geometry.Insets(10, 10, 10, 10));
        hBoxJoueurs2 = new HBox();
        hBoxJoueurs2.setAlignment(Pos.CENTER);
        hBoxJoueurs2.paddingProperty().setValue(new javafx.geometry.Insets(10, 10, 10, 10));
        hBoxJoueurs3 = new HBox();
        hBoxJoueurs3.setAlignment(Pos.CENTER);
        hBoxJoueurs3.paddingProperty().setValue(new javafx.geometry.Insets(10, 10, 10, 10));
        hBoxJoueurs4 = new HBox();
        hBoxJoueurs4.setAlignment(Pos.CENTER);
        hBoxJoueurs4.paddingProperty().setValue(new javafx.geometry.Insets(10, 10, 10, 10));

        // TextField pour entré le nom des joueurs (joueurs1, joueurs2, joueurs3, joueurs4) et si obligatoire

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

        // Label pour indiquer quel textField pour quel joueur

        labelJoueur1 = new Label("Joueur 1 : ");
        labelJoueur1.setStyle("-fx-font-size: 15px;");
        labelJoueur2 = new Label("Joueur 2 : ");
        labelJoueur2.setStyle("-fx-font-size: 15px;");
        labelJoueur3 = new Label("Joueur 3 : ");
        labelJoueur3.setStyle("-fx-font-size: 15px;");
        labelJoueur4 = new Label("Joueur 4 : ");
        labelJoueur4.setStyle("-fx-font-size: 15px;");

        // Ajout des éléments dans les HBox (Label avec textField correspondant)

        hBoxJoueurs1.getChildren().addAll(labelJoueur1, joueurs1);
        hBoxJoueurs2.getChildren().addAll(labelJoueur2, joueurs2);
        hBoxJoueurs3.getChildren().addAll(labelJoueur3, joueurs3);
        hBoxJoueurs4.getChildren().addAll(labelJoueur4, joueurs4);

        // Ajout des HBox dans la VBox qui permet l'affichage de la fenêtre

        vboxscene = new VBox();
        vboxscene.getChildren().addAll(hBoxtextHaut, hBoxJoueurs1, hBoxJoueurs2, hBoxJoueurs3, hBoxJoueurs4, hBoxButton);
        scene = new Scene(vboxscene, 400, 270);

        // Erreur quand le nombre de joueurs est inférieur à 2

        demarrerPartie.setOnAction(event -> {
            if (joueurs1.getText().isEmpty() || joueurs2.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur");
                alert.setContentText("Veuillez entrer un nom pour au moins 2 joueurs");
                alert.showAndWait();
            } else {
                nomsJoueurs.add(joueurs1.getText());
                nomsJoueurs.add(joueurs2.getText());
                if (!joueurs3.getText().isEmpty()) {
                    nomsJoueurs.add(joueurs3.getText());
                }
                if (!joueurs4.getText().isEmpty()) {
                    nomsJoueurs.add(joueurs4.getText());
                }
            }
        });
        setTitle("Choix des joueurs");
        setResizable(false);
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
