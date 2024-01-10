package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;

public class HomeController {
    
    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML TableView<CombinedOffer> latest_offers;

    @FXML public void initialize(){
        // Création des colonnes
        TableColumn<CombinedOffer, String> userNameColumn = new TableColumn<>("User Name");
        TableColumn<CombinedOffer, String> titleColumn = new TableColumn<>("Title");
        TableColumn<CombinedOffer, String> priceColumn = new TableColumn<>("Price");
        TableColumn<CombinedOffer, String> descriptionColumn = new TableColumn<>("Description");

        // Définir comment chaque colonne va obtenir ses valeurs
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner().getPrenom()));

    }

    @FXML public void handleDeconnexion(){
        System.out.println("Deconnexion de la session");
        skeleton_controller.main_controller.loadWelcomePage();
    }
    
}
