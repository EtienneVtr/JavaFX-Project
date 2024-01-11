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
    @FXML TableView<CombinedOffer> nearest_offers;

    private User currentUser;


    @FXML public void initialize(){
        currentUser = Main.getCurrentUser();

        // Création des colonnes
        TableColumn<CombinedOffer, String> typeColumn = new TableColumn<>("Type");
        TableColumn<CombinedOffer, String> userNameColumn = new TableColumn<>("User Name");
        TableColumn<CombinedOffer, String> titleColumn = new TableColumn<>("Title");
        TableColumn<CombinedOffer, String> priceColumn = new TableColumn<>("Price");
        TableColumn<CombinedOffer, String> datePublicationColumn = new TableColumn<>("Date Publication");
        TableColumn<CombinedOffer, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<CombinedOffer, String> cityColumn = new TableColumn<>("City");
        
        // Définir comment chaque colonne va obtenir ses valeurs
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeString()));
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerName()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        datePublicationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_publication()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner().getLocalisation()));

        // Ajoute les colonnes au TableView latest_offers
        latest_offers.getColumns().add(typeColumn);
        latest_offers.getColumns().add(userNameColumn);
        latest_offers.getColumns().add(titleColumn);
        latest_offers.getColumns().add(priceColumn);
        latest_offers.getColumns().add(datePublicationColumn);
        latest_offers.getColumns().add(descriptionColumn);
        latest_offers.getColumns().add(cityColumn);

        // Ajoute les colonnes au TableView nearest_offers
        nearest_offers.getColumns().add(typeColumn);
        nearest_offers.getColumns().add(userNameColumn);
        nearest_offers.getColumns().add(titleColumn);
        nearest_offers.getColumns().add(priceColumn);
        nearest_offers.getColumns().add(datePublicationColumn);
        nearest_offers.getColumns().add(descriptionColumn);
        nearest_offers.getColumns().add(cityColumn);

        // Ajoute les données au TableView
        ArrayList<EquipmentOffer> all_equipment = Main.getAllEquipment();
        ArrayList<ServiceOffer> all_service = Main.getAllService();
    
        ArrayList<CombinedOffer> all_offers = new ArrayList<>();
    
        // Seuil de distance pour filtrer les offres
        double distanceThreshold = 100000000.0; // 100 km
    
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
        // Trier par date de publication
        all_offers.sort((o1, o2) -> o2.getDate_publication().compareTo(o1.getDate_publication()));
        if(all_offers != null){
            latest_offers.setItems(FXCollections.observableArrayList(all_offers));
        }

        // Trier par distance
        all_offers.sort((o1, o2) -> {
            double distance1 = currentUser.calculateDistanceTo(o1.getOwner());
            double distance2 = currentUser.calculateDistanceTo(o2.getOwner());
            return Double.compare(distance1, distance2);
        });
        if(all_offers != null){
            nearest_offers.setItems(FXCollections.observableArrayList(all_offers));
        }

        latest_offers.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !latest_offers.getSelectionModel().isEmpty()) {
                CombinedOffer selected_item = latest_offers.getSelectionModel().getSelectedItem();
                handleDoubleClickOnEquipment(selected_item);
            }
        });
    }

    private void handleDoubleClickOnEquipment(CombinedOffer item) {
        // Ici, tu peux effectuer une action avec l'objet ServiceOffer sélectionné
        if(item.getTypeString().equals("Equipment")){
            EquipmentOffer equipment = new EquipmentOffer(item.getOwner().getMail(), item.getTitle(), item.getDescription(), item.getStartString(), item.getEstPris());
            skeleton_controller.loadEquipmentOfferPage(equipment);
        }else{
            ServiceOffer service = new ServiceOffer(item.getOwner().getMail(), item.getTitle(), item.getDescription(), item.getStartString(), item.getEstPris());
            skeleton_controller.loadServiceOfferPage(service);
        }
    }

    @FXML public void handleDeconnexion(){
        System.out.println("Deconnexion de la session");
        skeleton_controller.main_controller.loadWelcomePage();
    }
    
}
