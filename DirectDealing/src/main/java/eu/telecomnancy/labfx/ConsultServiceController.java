package eu.telecomnancy.labfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import java.util.ArrayList;

public class ConsultServiceController {

    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML private TextField keywords;
    @FXML private DatePicker begin;
    @FXML private DatePicker end;
    @FXML private Slider radius;
    @FXML private TableView<ServiceOffer> results;

    @FXML public void initialize() {
        // Création des colonnes
        TableColumn<ServiceOffer, String> userNameColumn = new TableColumn<>("User Name");
        TableColumn<ServiceOffer, String> titleColumn = new TableColumn<>("Title");
        TableColumn<ServiceOffer, String> priceColumn = new TableColumn<>("Price");
        TableColumn<ServiceOffer, String> dateColumn = new TableColumn<>("Date");
        TableColumn<ServiceOffer, String> timeColumn = new TableColumn<>("Time");
        TableColumn<ServiceOffer, String> descriptionColumn = new TableColumn<>("Description");

        // Définir comment chaque colonne va obtenir ses valeurs
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplier().getPrenom()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateStr()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimeStr()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        // Ajoute les colonnes au TableView
        results.getColumns().add(userNameColumn);
        results.getColumns().add(titleColumn);
        results.getColumns().add(priceColumn);
        results.getColumns().add(dateColumn);
        results.getColumns().add(timeColumn);
        results.getColumns().add(descriptionColumn);

        // Ajoute les données au TableView
        ArrayList<ServiceOffer> all_service = Main.getAllService();
        results.setItems(FXCollections.observableArrayList(all_service));

        results.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !results.getSelectionModel().isEmpty()) {
                ServiceOffer selectedService = results.getSelectionModel().getSelectedItem();
                handleDoubleClickOnService(selectedService);
            }
        });
    }
    
    private void handleDoubleClickOnService(ServiceOffer service) {
        // Ici, tu peux effectuer une action avec l'objet ServiceOffer sélectionné
        skeleton_controller.loadServiceOfferPage(service);
    }

    @FXML public void handleSearch() {
        System.out.println("Search");
    }

    @FXML public void handleReset() {
        System.out.println("Reset");
    }

    @FXML public void cancel() {
        System.out.println("cancel");
        skeleton_controller.loadServicePage();
    }
}
