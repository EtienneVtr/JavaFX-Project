package eu.telecomnancy.labfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultServiceController {

    private SkeletonController skeleton_controller;
    private User currentUser;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML private TextField keywords;
    @FXML private DatePicker begin;
    @FXML private DatePicker end;
    @FXML private Slider radius;
    @FXML private TableView<ServiceOffer> results;
    @FXML private TextField priceMin;
    @FXML private TextField priceMax;
    @FXML private TextField timeMinInput;
    @FXML private TextField timeMaxInput;


    @FXML public void initialize() {
        currentUser = Main.getCurrentUser();
        // Création des colonnes
        
        TableColumn<ServiceOffer, String> userNameColumn = new TableColumn<>("User Name");
        TableColumn<ServiceOffer, String> titleColumn = new TableColumn<>("Title");
        TableColumn<ServiceOffer, String> priceColumn = new TableColumn<>("Price");
        TableColumn<ServiceOffer, String> startColumn = new TableColumn<>("Start");
        TableColumn<ServiceOffer, String> endColumn = new TableColumn<>("End");
        TableColumn<ServiceOffer, String> timeColumn = new TableColumn<>("Time");
        TableColumn<ServiceOffer, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<ServiceOffer, String> cityColumn = new TableColumn<>("City");

        // Définir comment chaque colonne va obtenir ses valeurs
        userNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplier().getPrenom()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        startColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartStr()));
        endColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndStr()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimeStr()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplier().getLocalisation()));

        // Ajoute les colonnes au TableView
        results.getColumns().add(userNameColumn);
        results.getColumns().add(titleColumn);
        results.getColumns().add(priceColumn);
        results.getColumns().add(startColumn);
        results.getColumns().add(endColumn);
        results.getColumns().add(timeColumn);
        results.getColumns().add(descriptionColumn);
        results.getColumns().add(cityColumn);

        // Ajoute les données au TableView
        ArrayList<ServiceOffer> all_service = Main.getAllServiceHome();
        if(all_service != null){
            results.setItems(FXCollections.observableArrayList(all_service));
        }

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
        String keywordText = keywords.getText();
        LocalDate startDate = begin.getValue();
        LocalDate endDate = end.getValue();
        Integer minPrice = null;
        Integer maxPrice = null;
        String timeMin = timeMinInput.getText();
        String timeMax = timeMaxInput.getText();
        double selectedRadius = radius.getValue();
    
        try {
            if (!priceMin.getText().isEmpty()) {
                minPrice = Integer.parseInt(priceMin.getText());
            }
            if (!priceMax.getText().isEmpty()) {
                maxPrice = Integer.parseInt(priceMax.getText());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price");
        }
    
        // Vérifiez que les formats d'heure sont valides ou gérez les exceptions comme vous le souhaitez
    
        // Appeler une méthode pour effectuer la recherche dans la base de données
        List<ServiceOffer> searchResults = ServiceOffer.searchOffers(currentUser, keywordText, startDate, endDate, minPrice, maxPrice, timeMin, timeMax, selectedRadius);

        // Mettre à jour le TableView avec les résultats
        results.setItems(FXCollections.observableArrayList(searchResults));
    }
    
        

    @FXML public void handleReset() {
        System.out.println("Reset");
        skeleton_controller.loadListServiceOfferPage();
    }

    @FXML public void cancel() {
        System.out.println("cancel");
        skeleton_controller.loadServicePage();
    }
}
