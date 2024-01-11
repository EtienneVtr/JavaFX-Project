package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ServiceOfferController {

    private SkeletonController skeleton_controller;
    private ServiceOffer service_offer;
    private User currentUser;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML private Label title;
    @FXML private Label description;
    @FXML private Label date;
    @FXML private Label recurrency;
    @FXML private Label price;

    @FXML private Button book;
    @FXML private Button cancel;

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

    @FXML public void handleBook() {
        System.out.println("Tentative de réservation de l'offre");
        
        // Obtenir l'email de l'utilisateur actuel
        String currentUserEmail = Main.getCurrentUser().getMail();
    
        // Vérifier que l'utilisateur actuel n'est pas le fournisseur de l'offre
        if (currentUserEmail.equals(service_offer.getSupplierMail())) {
            System.out.println("Vous ne pouvez pas réserver votre propre offre");
            return;
        }
    
        // Vérifier qu'un utilisateur n'a pas déjà réservé l'offre
        if (service_offer.getEstPris() != null) {
            System.out.println("Cette offre a déjà été réservée");
            return;
        }

        // Tenter de réserver l'offre
        if (service_offer.reserveOffer(service_offer, currentUserEmail)) {
            System.out.println("Offre réservée avec succès");

            service_offer.setEstPris(currentUserEmail);
            //set nb florain user
            currentUser = Main.getCurrentUser();
            currentUser.setNbFlorain(currentUser.getNbFlorain() - service_offer.getPrice());
            skeleton_controller.updateProfile();
  
            skeleton_controller.loadListServiceOfferPage();
   
        } else {
            System.out.println("La réservation de l'offre a échoué");
        }
    }
    

    @FXML public void handleContact(){
        System.out.println("Contact !");
    }

    @FXML public void cancel(){
        System.out.println("Go back !");
        skeleton_controller.loadListServiceOfferPage();
    }
}
