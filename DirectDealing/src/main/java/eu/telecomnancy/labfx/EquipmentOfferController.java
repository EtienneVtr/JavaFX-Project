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
    private User currentUser;


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
        System.out.println("Tentative de réservation de l'équipement !");

        // Obtenir l'email de l'utilisateur actuel
        String currentUserEmail = Main.getCurrentUser().getMail();

        // Vérifier que l'utilisateur actuel n'est pas le fournisseur de l'offre
        if (currentUserEmail.equals(currentOffer.getMail())) {
            System.out.println("Vous ne pouvez pas réserver votre propre offre");
            return;
        }
        // Vérifier qu'un utilisateur n'a pas déjà réservé l'offre
        if (currentOffer.getEstPris() != null) {
            System.out.println("Cette offre a déjà été réservée");
            return;
        }

        // Tenter de réserver l'offre
        if (currentOffer.reserveOffer(currentOffer, currentUserEmail)) {
            System.out.println("Réservation réussie");

            currentOffer.setEstPris(currentUserEmail);
            System.out.println("L'offre est maintenant réservée par " + currentOffer.getEstPris() + "estpris: " + currentOffer.getEstPris());
            currentUser = Main.getCurrentUser();
            currentUser.setNbFlorain(currentUser.getNbFlorain() - currentOffer.getPrice());
            skeleton_controller.updateProfile();

            skeleton_controller.loadListEquipmentOfferPage();
        } else {
            System.out.println("Réservation échouée");
        }
    }

    @FXML public void handleContact(){
        System.out.println("Contact !");
    }

    @FXML public void cancel(){
        System.out.println("Go back !");
        skeleton_controller.loadListEquipmentOfferPage();
    }


}
