package eu.telecomnancy.labfx;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SkeletonController {

    private MainController main_controller;

    public void setMainController(MainController main_controller) {
        this.main_controller = main_controller;
    }

    @FXML
    private VBox menuContent;

    @FXML
    private VBox profileContent;

    @FXML
    private VBox mainContent;


    // Fonction qui permet de charger la home page:
    public void loadHomePage(){
        try {
            System.out.println("Chargement de la page Home");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/HomePage.fxml"));
            Parent home = loader.load();

            HomeController home_controller = loader.getController();
            home_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(home);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Vous pouvez également ajouter des méthodes spécifiques pour charger le menu et le profil si nécessaire
    public void loadMenuPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Menu.fxml"));
            Parent menu_page = loader.load();
            menuContent.getChildren().setAll(menu_page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Profile.fxml"));
            Parent profil_page = loader.load();
            profileContent.getChildren().setAll(profil_page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}


