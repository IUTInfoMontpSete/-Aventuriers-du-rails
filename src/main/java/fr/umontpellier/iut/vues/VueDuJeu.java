package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
    @FXML
    private VuePlateau plateau;
    @FXML
    private Label nbJoueurs, instructions;
    @FXML
    private VBox touslesjoueurs;
    @FXML
    private VueJoueurCourant joueurCourant;

    @FXML
    private HBox joueurSuivant1, joueurSuivant2, joueurSuivant3;

    //joueurs suivants
    @FXML
    private Label nomJS, nomJS1, nomJS2;

    //pioche
    @FXML
    private Label pioche1, pioche2, pioche3, pioche4, pioche5, pioche6;

    private ImageView carte6;

    private StageDestinationInitiales stageDestinationInitiales;

    private StageNouvellesDestination stageNouvellesDestination;

    private IJoueur iJoueur;

    //TODO: TOUT COMMENTER POUR QUE CE SOIT PROPRE

    //TODO: mettre le score visible pour tous : Fini
    //TODO : reziser les cartes wagon : Fini

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;

        plateau = new VuePlateau();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vueDuJeu.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stageDestinationInitiales = new StageDestinationInitiales(); // Fenetre de choix des destinations initiales
        stageDestinationInitiales.setJeu(jeu);

        stageNouvellesDestination = new StageNouvellesDestination(); // Fenetre de choix nouvelles destinations
        stageNouvellesDestination.setJeu(jeu);

        nbJoueurs.setText(": " + jeu.getJoueurs().size()); // Recupere le nombre de joueurs

        touslesjoueurs = new VBox();

        plateau.setJeu(jeu); // Set le Jeu

        carte6 = new ImageView("images/wagons.png"); // Carte pioche
        carte6.setFitWidth(80);
        carte6.setFitHeight(46);
        pioche6.setGraphic(carte6);

        joueurCourant.setJeu(jeu); // Set le Jeu

        if (jeu.getJoueurs().size() == 2) { // Affichage des Label en fonction du nombre de joueurs
            joueurSuivant2.setVisible(false);
            joueurSuivant3.setVisible(false);
        } else if (jeu.getJoueurs().size() == 3) {
            joueurSuivant3.setVisible(false);
        }
    }

    /**
     * Biding sur les éléments de la vue
     */
    public void creerBindings() {
//        plateau.creerBindings();

        jeu.instructionProperty().addListener((observable, oldValue, newValue) -> {
            instructions.setText(newValue);
        });


        joueurCourant.bindingCartesJoueur(); // Nombre de carte du joueur courant
        joueurCourant.creerBindings(); // Informations sur le joueur courant (nom, nombre de gares, nombre de wagons, nombre de cartes destinations)

        stageDestinationInitiales.destinationsInitialesBinding();

        piocheBinding(); // Pioche visible
        autresJoueursBinding(); // Affichage des joueurs suivants
        destinationsInitialesBinding(); // Listener pour les destinations initiales (Remove si choisi)

        stageNouvellesDestination.nouvellesDestinationsBinding();

        plateau.bindingVilles(); // Attribution des villes au proprietaires
        plateau.bindingRoutes(); // Attribution des routes au proprietaires

    }

    /**
     * Passer a été choisi
     */
    public void choixPasse() {
        jeu.passerAEteChoisi();
    }

    /**
     * Permet de set la taille des cartes wagon
     * @param couleur
     */
    public ImageView getImage(String couleur) {
        ImageView retour = new ImageView("images/cartesWagons/carte-wagon-" + couleur + ".png");
        retour.setFitWidth(80);
        retour.setFitHeight(45);
        return retour;
    }

    /**
     * Traduit la couleur du joueur en chaine de caractere pour afficher sa couleurs dans le jeu
     * @param ijoueur
     */
    public static String getCouleurTraduction(IJoueur ijoueur) {
        String couleur = ijoueur.getCouleur().toString();
        switch (couleur) {
            case "JAUNE":
                return "#ffd533";
            case "ROUGE":
                return "#cd4d45";
            case "BLEU":
                return "#718de4";
            case "VERT":
                return "#4feb55";
            case "ROSE":
                return "#ed7dc9";
            default:
                return "";
        }
    }

    /**
     * Si carte6 piocher (Aléatoire) sinon prendre carte afficher sur le plateau
     * @param event
     */
    @FXML
    public void choixPioche(MouseEvent event) {
        String id = ((Node) event.getSource()).getId();
        if (id.equals("pioche6")) {
            jeu.uneCarteWagonAEtePiochee();
        } else {
            int caseTableau = Integer.parseInt(id.substring(6)) - 1;
            jeu.uneCarteWagonAEteChoisie(jeu.cartesWagonVisiblesProperty().get(caseTableau));
        }
    }

    /**
     * Permet de mettre a jour les cartes wagon de la pioche
     */
    public void piocheBinding() {
        ListChangeListener<CouleurWagon> listenerPioche = new ListChangeListener<>() {

            @Override
            public void onChanged(Change<? extends CouleurWagon> change) {
                Platform.runLater(() -> {
                    pioche1.setGraphic(getImage(change.getList().get(0).toString()));
                    pioche2.setGraphic(getImage(change.getList().get(1).toString()));
                    pioche3.setGraphic(getImage(change.getList().get(2).toString()));
                    pioche4.setGraphic(getImage(change.getList().get(3).toString()));
                    pioche5.setGraphic(getImage(change.getList().get(4).toString()));
                });
            }
        };
        jeu.cartesWagonVisiblesProperty().addListener(listenerPioche);
    }

    /**
     * Fonctionnement de l'ordre de passage des joueurs
     */
    public void autresJoueursBinding() {
        ChangeListener<IJoueur> listenerAutresJoueurs = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur joueur, IJoueur t1) {
                Platform.runLater(() -> {
                    int indexJoueurCourant = jeu.getJoueurs().indexOf((Joueur) jeu.joueurCourantProperty().get());
                    nomJS.setText(jeu.getJoueurs().get((indexJoueurCourant + 1) % jeu.getJoueurs().size()).getNom() + " - " + jeu.getJoueurs().get((indexJoueurCourant + 1) % jeu.getJoueurs().size()).getScore() + " Pts");
                    nomJS.setStyle("-fx-background-color: " + getCouleurTraduction(jeu.getJoueurs().get((indexJoueurCourant + 1) % jeu.getJoueurs().size())) + "; -fx-background-radius: 5;");
                    nomJS1.setText(jeu.getJoueurs().get((indexJoueurCourant + 2) % jeu.getJoueurs().size()).getNom() + " - " + jeu.getJoueurs().get((indexJoueurCourant + 2) % jeu.getJoueurs().size()).getScore() + " Pts");
                    nomJS1.setStyle("-fx-background-color: " + getCouleurTraduction(jeu.getJoueurs().get((indexJoueurCourant + 2) % jeu.getJoueurs().size())) + "; -fx-background-radius: 5;");
                    nomJS2.setText(jeu.getJoueurs().get((indexJoueurCourant + 3) % jeu.getJoueurs().size()).getNom() + " - " + jeu.getJoueurs().get((indexJoueurCourant + 3) % jeu.getJoueurs().size()).getScore() + " Pts");
                    nomJS2.setStyle("-fx-background-color: " + getCouleurTraduction(jeu.getJoueurs().get((indexJoueurCourant + 3) % jeu.getJoueurs().size())) + "; -fx-background-radius: 5;");
                });
            }
        };

        jeu.joueurCourantProperty().addListener(listenerAutresJoueurs);
    }

    /**
     * Listener pour les destinations initiales (Remove si choisi)
     */
    public void destinationsInitialesBinding() {
        ListChangeListener<Destination> listenerDestinationsInitiales = new ListChangeListener<>() {

            @Override
            public void onChanged(Change<? extends Destination> change) {
                Platform.runLater(() -> {
                    if (stageDestinationInitiales.getStatusFini()) {
                        stageDestinationInitiales.close();
                        jeu.destinationsInitialesProperty().removeListener(this);
                    } else {
                        stageDestinationInitiales.show();
                    }
                });
            }
        };
        jeu.destinationsInitialesProperty().addListener(listenerDestinationsInitiales);
    }

    /**
     * Si button "Nouvelle Destination" cliqué, afficher la fenetre de Nouvelle Destination
     */
    @FXML
    public void choisirDestination() {
        jeu.uneDestinationAEtePiochee();
        stageNouvellesDestination.show();
    }

}