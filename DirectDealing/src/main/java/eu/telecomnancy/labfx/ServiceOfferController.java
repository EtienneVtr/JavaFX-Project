package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;

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

    @FXML private DatePicker booking_begin;
    @FXML private DatePicker booking_end;

    public void setCurrentOffer(ServiceOffer offer) {
        this.service_offer = offer;
        displayOfferInfo();
    }

    private void displayOfferInfo() {
        title.setText("Titre : " + service_offer.getTitle());
        description.setText("Description : " + service_offer.getDescription());
        String startDateString = service_offer.getStart() != null ? service_offer.getStart().toString() : "Pas de date renseigné";
        String endDateString = service_offer.getEnd() != null ? service_offer.getEnd().toString() : "Pas de date renseigné";
        String time = service_offer.getTime() != null ? service_offer.getTime().toString() : "Pas d'heure renseigné";
        date.setText("Date : " + startDateString + " to " + endDateString + " at " + time);
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

        LocalDate begin = booking_begin.getValue();
        LocalDate end = booking_end.getValue();

        if(begin == null || end == null){
            System.out.println("Veuillez renseigner une date de début et de fin");
            return;
        }else if(begin.isAfter(end)){
            System.out.println("La date de début doit être avant la date de fin");
            return;
        }else if(begin.isBefore(service_offer.getStart()) || end.isAfter(service_offer.getStart())){
            System.out.println("La date de début et de fin doivent être comprises dans la période de disponibilité de l'offre");
            return;
        }


        // Tenter de réserver l'offre
        if (service_offer.reserveOffer(service_offer, currentUserEmail, begin, end)) {
            System.out.println("Offre réservée avec succès");

            // Création de deux nouvelles offres avant et après la période de réservation
            // si le premier jour de réservation est le même que le début de l'offre de base, on en crée qu'une après
            // inverse pour le dernier jour de réservation
            /* if(begin.equals(service_offer.getStart()) && end.equals(service_offer.getEnd())){
                System.out.println("Offre bien créée");
            }else if(begin.equals(service_offer.getStart())){
                System.out.println("Offre bien créée");
                ServiceOffer newOffer = new ServiceOffer(service_offer.getTitle(), service_offer.getDescription(), service_offer.getSupplierMail(), service_offer.getPrice(), end.plusDays(1), service_offer.getEnd(), service_offer.getRecurrency(), service_offer.getDaysOfService(), service_offer.getTime());



            service_offer.setEstPris(currentUserEmail); */
            //set nb florain user
            currentUser = Main.getCurrentUser();
            currentUser.setNbFlorain(currentUser.getNbFlorain() - service_offer.getPrice());
            skeleton_controller.updateProfile();
  
   
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
