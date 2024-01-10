package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServiceOfferController {

    private SkeletonController skeleton_controller;
    private ServiceOffer service_offer;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML private Label title;
    @FXML private Label description;
    @FXML private Label date;
    @FXML private Label recurrency;
    @FXML private Label price;

    public void setCurrentOffer(ServiceOffer offer) {
        this.service_offer = offer;
        displayOfferInfo();
    }

    private void displayOfferInfo() {
        title.setText("Titre : " + service_offer.getTitle());
        description.setText("Description : " + service_offer.getDescription());
        String startDateString = service_offer.getDate() != null ? service_offer.getDate().toString() : "Pas de date renseigné";
        String time = service_offer.getTime() != null ? service_offer.getTime().toString() : "Pas d'heure renseigné";
        date.setText("Date : " + startDateString + " / " + time);
        recurrency.setText("Récurrence : " + service_offer.getRecurrency() + " jours par semaine" + "(" + service_offer.getDaysOfService() + ")");
        price.setText("Coût en florains : " + service_offer.getPrice());
    }

    @FXML public void handleBook(){
        System.out.println("Book !");
    }

    @FXML public void handleContact(){
        System.out.println("Contact !");
    }

    @FXML public void cancel(){
        System.out.println("Go back !");
        skeleton_controller.loadListServiceOfferPage();
    }
}
