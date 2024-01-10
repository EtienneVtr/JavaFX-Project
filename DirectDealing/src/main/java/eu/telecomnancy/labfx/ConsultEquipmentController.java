package eu.telecomnancy.labfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import java.util.ArrayList;

public class ConsultEquipmentController {

    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML private TextField keywords;
    @FXML private DatePicker begin;
    @FXML private DatePicker end;
    @FXML private Slider radius;
    @FXML private TableView<EquipmentOffer> results;

    @FXML public void initialize() {
        // Création des colonnes
        TableColumn<EquipmentOffer, String> userNameColumn = new TableColumn<>("User Name");
        TableColumn<EquipmentOffer, String> titleColumn = new TableColumn<>("Title");
        TableColumn<EquipmentOffer, String> priceColumn = new TableColumn<>("Price");
        TableColumn<EquipmentOffer, String> dateColumn = new TableColumn<>("Date");
        TableColumn<EquipmentOffer, String> descriptionColumn = new TableColumn<>("Description");

        // Définir comment chaque colonne va obtenir ses valeurs
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner().getPrenom()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartAvailabilityStr()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        // Ajoute les colonnes au TableView
        results.getColumns().add(userNameColumn);
        results.getColumns().add(titleColumn);
        results.getColumns().add(priceColumn);
        results.getColumns().add(dateColumn);
        results.getColumns().add(descriptionColumn);

        // Ajoute les données au TableView
        ArrayList<EquipmentOffer> all_equipment = Main.getAllEquipment();
        if(all_equipment != null){
            results.setItems(FXCollections.observableArrayList(all_equipment));
        }

        results.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !results.getSelectionModel().isEmpty()) {
                EquipmentOffer selectedEquipment = results.getSelectionModel().getSelectedItem();
                handleDoubleClickOnEquipment(selectedEquipment);
            }
        });
    }

    private void handleDoubleClickOnEquipment(EquipmentOffer equipment) {
        // Ici, tu peux effectuer une action avec l'objet ServiceOffer sélectionné
        skeleton_controller.loadEquipmentOfferPage(equipment);
    }

    @FXML public void handleSearch() {
        System.out.println("Search");
    }

    @FXML public void handleReset() {
        System.out.println("Reset");
    }

    @FXML public void cancel(){
        System.out.println("Go back !");
        skeleton_controller.loadEquipmentPage();
    }
}
