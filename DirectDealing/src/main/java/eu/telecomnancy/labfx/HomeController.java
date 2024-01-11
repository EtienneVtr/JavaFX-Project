package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.util.ArrayList;

public class HomeController {
    
    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML TableView<CombinedOffer> latest_offers;

    private User currentUser;


    @FXML public void initialize(){
        // Création des colonnes
        TableColumn<CombinedOffer, String> typeColumn = new TableColumn<>("Type");
        TableColumn<CombinedOffer, String> userNameColumn = new TableColumn<>("User Name");
        TableColumn<CombinedOffer, String> titleColumn = new TableColumn<>("Title");
        TableColumn<CombinedOffer, String> priceColumn = new TableColumn<>("Price");
        TableColumn<CombinedOffer, String> datePublicationColumn = new TableColumn<>("Date Publication");
        TableColumn<CombinedOffer, String> descriptionColumn = new TableColumn<>("Description");
        currentUser = Main.getCurrentUser();
        // Définir comment chaque colonne va obtenir ses valeurs
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeString()));
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerName()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        datePublicationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_publication()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        // Ajoute les colonnes au TableView
        latest_offers.getColumns().add(typeColumn);
        latest_offers.getColumns().add(userNameColumn);
        latest_offers.getColumns().add(titleColumn);
        latest_offers.getColumns().add(priceColumn);
        latest_offers.getColumns().add(datePublicationColumn);
        latest_offers.getColumns().add(descriptionColumn);

        // Ajoute les données au TableView
        ArrayList<EquipmentOffer> all_equipment = Main.getAllEquipment();
        ArrayList<ServiceOffer> all_service = Main.getAllService();
    
        ArrayList<CombinedOffer> all_offers = new ArrayList<>();
    
        // Seuil de distance pour filtrer les offres
        double distanceThreshold = 100.0; // 100 km
    
        // Ajouter les offres d'équipement après les avoir filtrées
        if(all_equipment != null){
            for(EquipmentOffer equipment : all_equipment){
                if (currentUser.calculateDistanceTo(equipment.getOwner()) <= distanceThreshold) {
                    all_offers.add(new CombinedOffer(equipment));
                }
            }
        }
    
        // Ajouter les offres de service après les avoir filtrées
        if(all_service != null){
            for(ServiceOffer service : all_service){
                if (currentUser.calculateDistanceTo(service.getSupplier()) <= distanceThreshold) {
                    all_offers.add(new CombinedOffer(service));
                }
            }
        }
    
        // Trier et afficher les offres
        all_offers.sort((o1, o2) -> o2.getDate_publication().compareTo(o1.getDate_publication()));
        if(all_offers != null){
            latest_offers.setItems(FXCollections.observableArrayList(all_offers));
        }
    }

    @FXML public void handleDeconnexion(){
        System.out.println("Deconnexion de la session");
        skeleton_controller.main_controller.loadWelcomePage();
    }
    
}
