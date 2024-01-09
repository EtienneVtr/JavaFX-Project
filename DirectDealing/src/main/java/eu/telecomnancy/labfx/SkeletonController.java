package eu.telecomnancy.labfx;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SkeletonController {

    public MainController main_controller;

    public void setMainController(MainController main_controller) {
        this.main_controller = main_controller;
    }

    @FXML
    private SplitPane skeletonContent;

    @FXML
    private VBox menuContent;

    @FXML
    private VBox profileContent;

    @FXML
    private VBox mainContent;


    private User currentUser;


    public void initialize(){
        System.out.println("Initialisation de la session");
        currentUser = Main.getCurrentUser();
    }

    // Vous pouvez également ajouter des méthodes spécifiques pour charger le menu et le profil si nécessaire
    public void loadMenuPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Menu.fxml"));
            Parent menu_page = loader.load();

            MenuController menu_controller = loader.getController();
            menu_controller.setSkeletonController(this);

            menuContent.getChildren().setAll(menu_page);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Profile.fxml"));
            Parent profil_page = loader.load();

            ProfileController profile_controller = loader.getController();
            profile_controller.setSkeletonController(this);

            profileContent.getChildren().setAll(profil_page);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



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


    // Fonction qui permet de charger la page des services
    public void loadServicePage(){
        try {
            System.out.println("Chargement de la page Service");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Service.fxml"));
            Parent service = loader.load();

            ServiceController service_controller = loader.getController();
            service_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(service);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void loadEquipmentPage(){
        try {
            System.out.println("Chargement de la page Equipment");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Equipment.fxml"));
            Parent equipment = loader.load();

            EquipmentController equipment_controller = loader.getController();
            equipment_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(equipment);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}