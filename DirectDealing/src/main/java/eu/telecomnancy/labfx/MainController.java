package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class MainController {

    // Fonction qui permet de charger la page de bienvenue
    public void loadWelcomePage() {
        System.out.println("Chargement de la page de bienvenue");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/WelcomePage.fxml"));
            Parent welcomePage = loader.load();

            // Ajouter le WelcomePage à la scène
            Main.getPrimaryStage().getScene().setRoot(welcomePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Fonction qui permet de charger la page d'inscription
    public void loadInscriptionPage() {
        try {
            System.out.println("Chargement de la page d'inscription");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/InscriptionPage.fxml"));
            Parent inscription = loader.load();

            InscriptionController inscriptionController = loader.getController();
            inscriptionController.setMainController(this);

            // Ajouter la page d'inscription à la scène
            Main.getPrimaryStage().getScene().setRoot(inscription);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Bouton qui charge la page d'incription
    @FXML
    public void handleInscription() throws IOException {
        loadInscriptionPage();
    }

    // Bouton qui charge la page de bienvenue
    @FXML
    public void handleWelcome() throws IOException {
        loadWelcomePage();
    }


}
