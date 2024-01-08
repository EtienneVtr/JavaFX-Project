package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public void initialize() {
        // Charger le contenu de WelcomePage.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/WelcomePage.fxml"));
            Parent welcomePage = loader.load();

            // Ajouter le WelcomePage à l'AnchorPane parent
            mainAnchorPane.getChildren().add(welcomePage);

            // Configurer les contraintes pour que le WelcomePage remplisse l'AnchorPane
            AnchorPane.setTopAnchor(welcomePage, 0.0);
            AnchorPane.setBottomAnchor(welcomePage, 0.0);
            AnchorPane.setLeftAnchor(welcomePage, 0.0);
            AnchorPane.setRightAnchor(welcomePage, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Autres méthodes du contrôleur, si nécessaire

    // Exemple de méthode pour accéder au Stage depuis le contrôleur
    public void setPrimaryStage(Stage primaryStage) {
        Main.setCurrentStage(primaryStage);
    }
}
