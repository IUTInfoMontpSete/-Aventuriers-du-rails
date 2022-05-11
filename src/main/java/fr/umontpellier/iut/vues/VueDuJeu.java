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
    /**
     * private VuePlateau plateau;
     */
    private static ListChangeListener<Destination> listenersdestinations;
    private Button passer;
    private VBox listesdestinations;


    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        /** plateau = new VuePlateau(); */
        /** getChildren().add(plateau);*/
        this.passer = new Button("Passer");
        getChildren().add(passer);
        this.listesdestinations = new VBox();
        passer.setOnAction(e -> {
            jeu.passerAEteChoisi();
        });
        listesdestinations.setStyle("-fx-border-color: black;");
        getChildren().add(listesdestinations);
        setPrefSize(200, 400);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {

        listenersdestinations = (ListChangeListener.Change<? extends Destination> c) -> {
            Platform.runLater(() -> {
                while (c.next()) {
                    Label labelDestChoisi = new Label();
                    labelDestChoisi.setPrefSize(200, 20);
                    if (c.wasAdded()) {
                        for (Destination i : c.getAddedSubList()) {
                            labelDestChoisi.setText(i.getNom());
                            listesdestinations.getChildren().add(labelDestChoisi);


                            System.out.println("ADD : " + i.getNom());


                        }
                    }
                    if (c.wasRemoved()) {
                        for (Destination i : c.getRemoved()) {
                            if (!listesdestinations.getChildren().isEmpty()) {
                                listesdestinations.getChildren().remove(labelDestChoisi);
                            }


                            System.out.println("REMOVE : " + i.getNom());

                        }
                    }
                }
            });
        };
        jeu.destinationsInitialesProperty().addListener(listenersdestinations);
    }

    public static ListChangeListener<Destination> getListenersdestinations() {
        return listenersdestinations;
    }

}
