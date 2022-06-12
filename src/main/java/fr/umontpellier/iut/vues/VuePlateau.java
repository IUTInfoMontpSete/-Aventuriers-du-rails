package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Joueur;
import fr.umontpellier.iut.rails.Route;
import fr.umontpellier.iut.rails.Ville;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 * <p>
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {

    IJeu jeu;

    public VuePlateau() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setJeu(IJeu jeu) {
        this.jeu = jeu;
    }

    @FXML
    public void choixRouteOuVille(MouseEvent event) {
        String RouteOuVille = ((Node) event.getSource()).getId();
        this.jeu.uneVilleOuUneRouteAEteChoisie(RouteOuVille);
    }

    /**
     * Association de la ville choisi avec sont futur propietaire
     */
    public void bindingVilles() {
        ArrayList<Ville> villes = new ArrayList<>();
        for (int i = 0; i < this.jeu.getVilles().size(); i++) {
            villes.add((Ville) this.jeu.getVilles().get(i));
        }

        ChangeListener<Joueur> bindProprioVille = new ChangeListener<Joueur>() {
            @Override
            public void changed(ObservableValue<? extends Joueur> observableValue, Joueur joueur, Joueur t1) {
                Platform.runLater(() -> {
                    for (Ville ville : villes) {
                        if (ville.proprietaireProperty().get() != null && ville.proprietaireProperty().get().equals(t1)) {
                            Circle circle = (Circle) lookup("#" + ville.getNom());

                            circle.setFill(Paint.valueOf(VueDuJeu.getCouleurTraduction(t1)));
                            circle.setStroke(Paint.valueOf("black"));
                            circle.setStrokeWidth(2);
                        }
                    }
                });
            }
        };

        for (Ville ville : villes) {
            ville.proprietaireProperty().addListener(bindProprioVille);
        }

    }

    public void bindingRoutes() {
        ArrayList<Route> routes = new ArrayList<>();
        for (int i = 0; i < this.jeu.getRoutes().size(); i++) {
            routes.add((Route) this.jeu.getRoutes().get(i));
        }

        ChangeListener<Joueur> bindProprioRoute = new ChangeListener<Joueur>() {
            @Override
            public void changed(ObservableValue<? extends Joueur> observableValue, Joueur joueur, Joueur t1) {
                Platform.runLater(() -> {
                    for (int i = 0; i < routes.size(); i++) {
                        if (routes.get(i).proprietaireProperty().get() != null && routes.get(i).proprietaireProperty().get().equals(t1)) {
                            VuePlateau plateautest = (VuePlateau) lookup("#plateau");
                            Group routesGroup = (Group) plateautest.lookup("#routes");
                            Group routeActuelle = (Group) routesGroup.getChildren().get(i);

                            for (int j = 0; j < routeActuelle.getChildren().size(); j++) {
                                Rectangle rectangle = (Rectangle) routeActuelle.getChildren().get(j);
                                rectangle.setFill(Paint.valueOf(VueDuJeu.getCouleurTraduction(t1)));
                                rectangle.setStroke(Paint.valueOf("black"));
                                rectangle.setStrokeWidth(2);
                            }
                        }
                    }
                });
            }
        };

        for (Route route : routes) {
            route.proprietaireProperty().addListener(bindProprioRoute);
        }

    }


    @FXML
    ImageView image;
    @FXML
    private Group villes;
    @FXML
    private Group routes;

    public void creerBindings() {
        bindRedimensionPlateau();
    }

    private void bindRedimensionPlateau() {
        bindRoutes();
        bindVilles();
//        Les dimensions de l'image varient avec celle de la scène
        image.fitWidthProperty().bind(getScene().widthProperty());
        image.fitHeightProperty().bind(getScene().heightProperty());
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {
//        Liste des propriétés à lier
//        rect.widthProperty()
//        rect.heightProperty()
//        rect.layoutXProperty()
//        rect.xProperty()
//        rect.layoutYProperty()
//        rect.yProperty()
    }

    private void bindRoutes() {
        for (Node nRoute : routes.getChildren()) {
            Group gRoute = (Group) nRoute;
            int numRect = 0;
            for (Node nRect : gRoute.getChildren()) {
                Rectangle rect = (Rectangle) nRect;
                bindRectangle(rect, DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutX(), DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutY());
                numRect++;
            }
        }
    }

    private void bindVilles() {
        for (Node nVille : villes.getChildren()) {
            Circle ville = (Circle) nVille;
            bindVille(ville, DonneesPlateau.getVille(ville.getId()).getLayoutX(), DonneesPlateau.getVille(ville.getId()).getLayoutY());
        }
    }

    private void bindVille(Circle ville, double layoutX, double layoutY) {
        ville.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
        ville.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
        ville.radiusProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesPlateau.rayonInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
    }

}
