package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

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

    @FXML private DatePicker booking_begin;
    @FXML private DatePicker booking_end;

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

        LocalDate begin = booking_begin.getValue();
        LocalDate end = booking_end.getValue();

        if(begin == null || end == null){
            System.out.println("Veuillez renseigner une date de début et de fin");
            return;
        }else if(begin.isAfter(end)){
            System.out.println("La date de début doit être avant la date de fin");
            return;
        }else if(begin.isBefore(currentOffer.getStartAvailability()) || end.isAfter(currentOffer.getEndAvaibility())){
            System.out.println("La date de début et de fin doivent être comprises dans la période de disponibilité de l'offre");
            return;
        }



        // Tenter de réserver l'offre
        if (currentOffer.reserveOffer(currentOffer, currentUserEmail, begin, end)) {
            System.out.println("Réservation réussie");

            // Création de deux nouvelles offres avant et après la période de réservation
            // si le premier jour de réservation est le même que le début de l'offre de base, on en crée qu'une après
            // inverse pour le dernier jour de réservation
            if(begin.equals(currentOffer.getStartAvailability()) && end.equals(currentOffer.getEndAvaibility())){
                System.out.println("Offre bien créée");
            }else if(begin.equals(currentOffer.getStartAvailability())){
                EquipmentOffer newOffer = new EquipmentOffer(currentOffer.getOwner(), currentOffer.getName(), currentOffer.getDescription(), currentOffer.getQuantity(), end.plusDays(1), currentOffer.getEndAvaibility(), currentOffer.getPrice());
                System.out.println("Offre bien créée");
            }else if(end.equals(currentOffer.getEndAvaibility())){
                EquipmentOffer newOffer = new EquipmentOffer(currentOffer.getOwner(), currentOffer.getName(), currentOffer.getDescription(), currentOffer.getQuantity(), currentOffer.getStartAvailability(), begin.minusDays(1), currentOffer.getPrice());
                System.out.println("Offre bien créée");
            }else{
                EquipmentOffer newOffer1 = new EquipmentOffer(currentOffer.getOwner(), currentOffer.getName(), currentOffer.getDescription(), currentOffer.getQuantity(), currentOffer.getStartAvailability(), begin.minusDays(1), currentOffer.getPrice());
                EquipmentOffer newOffer2 = new EquipmentOffer(currentOffer.getOwner(), currentOffer.getName(), currentOffer.getDescription(), currentOffer.getQuantity(), end.plusDays(1), currentOffer.getEndAvaibility(), currentOffer.getPrice());
                System.out.println("Offres bien créées");
            }

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
        System.out.println("Lancement d'une conversation avec le propriétaire de l'offre");
    
        // Obtenir le pseudo du fournisseur
        String supplierPseudo = currentOffer.getOwner().getPseudo();
    
        // Passer le pseudo du fournisseur au SkeletonController
        skeleton_controller.setSupplierForMessaging(supplierPseudo);
    
        // Charger la page de messagerie
        skeleton_controller.loadMessageriePage();
    }

    @FXML public void cancel(){
        System.out.println("Go back !");
        skeleton_controller.loadListEquipmentOfferPage();
    }


}
