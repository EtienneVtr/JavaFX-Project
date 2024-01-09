package eu.telecomnancy.labfx;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CreateEquipmentController {
    @FXML private TextField title;
    @FXML private TextField description;
    @FXML private TextField price;
    @FXML private TextField quantity;
    @FXML private DatePicker begin;
    @FXML private DatePicker end;

    private User currentUser;
    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML
    public void initialize() {
        System.out.println("EquipmentController initialize");
        currentUser = Main.getCurrentUser();
    }   

    @FXML public void handleCreateOffer() {
        System.out.println("Create offer");
        String titleField = title.getText();
        String descriptionField = description.getText();
        String priceStr = price.getText();
        String quantityStr = quantity.getText();
    
        if (titleField.isEmpty() || priceStr.isEmpty() || descriptionField.isEmpty() || quantityStr.isEmpty()) {
            // Afficher un message d'erreur ou gérer l'erreur
            System.out.println("Veuillez remplir tous les champs requis.");
            return;
        }
    
        try {
            int price = Integer.parseInt(priceStr);
            int quantity = Integer.parseInt(quantityStr);
    
            LocalDate startDate = begin.getValue(); // Peut être null
            LocalDate endDate = end.getValue(); // Peut être null
    
            EquipmentOffer newOffer = new EquipmentOffer(currentUser.getMail());
            newOffer.setTitle(titleField);
            newOffer.setDescription(descriptionField);
            newOffer.setPrice(price);
            newOffer.setQuantity(quantity);
            newOffer.setStartAvailability(startDate);
            newOffer.setEndAvailability(endDate);
            

        } catch (NumberFormatException e) {
            System.out.println("Erreur de format de nombre");
        }
    }
    

    @FXML public void cancel() {
        System.out.println("cancel");
    }
}