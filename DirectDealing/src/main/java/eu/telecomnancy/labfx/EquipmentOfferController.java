package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EquipmentOfferController {
    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    
    
    @FXML private Label title;
    @FXML private Label description;
    @FXML private Label quantity;
    @FXML private Label dates;
    @FXML private Label price;

    private EquipmentOffer currentOffer;

    public void setCurrentOffer(EquipmentOffer offer) {
        this.currentOffer = offer;
        displayOfferInfo();
    }

    private void displayOfferInfo() {
        title.setText("Titre : " + currentOffer.getName());
        description.setText("Description : " + currentOffer.getDescription());
        quantity.setText("Quantité : " + currentOffer.getQuantity());    
        String startDateString = currentOffer.getStartAvailability() != null ? currentOffer.getStartAvailability().toString() : "Pas de date renseigné";
        String endDateString = currentOffer.getEndAvaibility() != null ? currentOffer.getEndAvaibility().toString() : "Pas de date renseigné";
        dates.setText("Dates : " + startDateString + " / " + endDateString);    
        price.setText("Coût en florains : " + currentOffer.getPrice());
    }

    @FXML public void handleBook(){
        System.out.println("Book !");
    }

    @FXML public void handleContact(){
        System.out.println("Contact !");
    }

    @FXML public void cancel(){
        System.out.println("Go back !");
    }


}
