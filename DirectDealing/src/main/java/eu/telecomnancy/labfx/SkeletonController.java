package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import java.util.ArrayList;
import java.io.IOException;

public class SkeletonController {

    public MainController main_controller;

    public void setMainController(MainController main_controller) {
        this.main_controller = main_controller;
    }

    @FXML

    private SplitPane skeletonContent;

    @FXML
    private VBox menuContent;

    @FXML private ProfileController profil_controller;

    @FXML
    private VBox profileContent;

    @FXML
    private VBox mainContent;


    private User currentUser;


    public void initialize(){
        System.out.println("Initialisation de la session");
        currentUser = Main.getCurrentUser();
    }

    public void setProfileController(ProfileController profil_controller){
        this.profil_controller = profil_controller;
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
            setProfileController(profile_controller);
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
            System.out.println("Chargement de la page matériel");
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

    // Fonction qui permet de charger la page de création des équipements
    public void loadCreateEquipmentPage(){
        try {
            System.out.println("Chargement de la page de création d'une offre d'équipement");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/CreateEquipment.fxml"));
            Parent create_equipment = loader.load();

            CreateEquipmentController create_equipment_controller = loader.getController();
            create_equipment_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(create_equipment);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadEquipmentOfferPage(EquipmentOffer offer){
        try {
            System.out.println("Chargement de la page d'une offre d'équipement");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/EquipmentOffer.fxml"));
            Parent equipment_offer = loader.load();

            EquipmentOfferController equipment_offer_controller = loader.getController();
            equipment_offer_controller.setSkeletonController(this);
            equipment_offer_controller.setCurrentOffer(offer);


            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(equipment_offer);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction qui permet de charger la page de liste des offres d'équipement
    public void loadListEquipmentOfferPage(){
        try {
            System.out.println("Chargement de la page de liste des offres d'équipement");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/ConsultEquipment.fxml"));
            Parent list_equipment_offer = loader.load();

            ConsultEquipmentController list_equipment_offer_controller = loader.getController();
            list_equipment_offer_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(list_equipment_offer);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction qui permet de charger la page de liste des offres de service
    public void loadListServiceOfferPage(){
        try {
            System.out.println("Chargement de la page de liste des offres de service");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/ConsultService.fxml"));
            Parent list_service_offer = loader.load();

            ConsultServiceController list_service_offer_controller = loader.getController();
            list_service_offer_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(list_service_offer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Fonction qui permet de charger la page de profile privé
    public void loadPrivateProfile() {
        try {
            System.out.println("Chargement de la page de profile privé");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/PrivateProfilePage.fxml"));
            Parent privateProfile = loader.load();

            PrivateProfileController privateProfile_controller = loader.getController();
            privateProfile_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(privateProfile);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction qui permet de charger la page d'affichage d'une offre de service
    public void loadServiceOfferPage(ServiceOffer offer){
        try {
            System.out.println("Chargement de la page d'une offre de service");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/ServiceOffer.fxml"));
            Parent service_offer = loader.load();
            ServiceOfferController service_offer_controller = loader.getController();
            service_offer_controller.setSkeletonController(this);
            service_offer_controller.setCurrentOffer(offer);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(service_offer);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction qui permet de charger la page de création d'une offre de service
    public void loadCreateServicePage(){
        try {
            System.out.println("Chargement de la page de création d'une offre de service");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/CreateService.fxml"));
            Parent create_service = loader.load();

            CreateServiceController create_service_controller = loader.getController();
            create_service_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(create_service);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction qui permet de charger la page de la carte
    public void loadMapPage(){
        try {
            System.out.println("Chargement de la Map page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Map.fxml"));
            Parent map = loader.load();

            MapController map_controller = loader.getController();
            map_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(map);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction qui permet de charger la page du planning
    public void loadPlanningPage(){
        try {
            System.out.println("Chargement de la page planning");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Planning.fxml"));
            Parent planning = loader.load();

            PlanningController planning_controller = loader.getController();
            planning_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(planning);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction qui permet de charger la page de la messagerie
    public void loadMessageriePage(){
        try {
            System.out.println("Chargement de la page messagerie");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Messagerie.fxml"));
            Parent messagerie = loader.load();

            MessagerieController messagerie_controller = loader.getController();
            messagerie_controller.setSkeletonController(this);

            // Ajouter la page d'inscription à la scène
            mainContent.getChildren().setAll(messagerie);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateProfile(){
        profil_controller.updateProfileInfo(currentUser);
    }

    
}


