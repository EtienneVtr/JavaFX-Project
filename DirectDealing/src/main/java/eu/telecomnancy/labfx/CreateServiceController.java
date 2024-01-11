package eu.telecomnancy.labfx;

import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.LocalTime;


public class CreateServiceController {



    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    private User currentUser = Main.getCurrentUser();


    @FXML private TextField title;
    @FXML private TextField description;
    @FXML private DatePicker start;
    @FXML private DatePicker end;
    @FXML private TextField time;
    @FXML private TextField price;

    @FXML public void handleCreateOffer() {
            System.out.println("Create service");

            String titleField = title.getText();
            String descriptionField = description.getText();
            String priceStr = price.getText();
            
            if (titleField.isEmpty() || descriptionField.isEmpty() || priceStr.isEmpty()) {
                System.out.println("Veuillez remplir tous les champs requis.");
                return;
            }
            
            try{
                int price = Integer.parseInt(priceStr);

                LocalDate startField = start.getValue();
                LocalDate endField = end.getValue();
                LocalTime time = LocalTime.parse(this.time.getText()); //transform textfield to local time
                
                if (startField == null || endField == null || time == null) {
                    System.out.println("Veuillez remplir tous les champs requis.");
                    return;
                }
                
                ServiceOffer newOffer = new ServiceOffer(
                    currentUser.getMail(), 
                    titleField, 
                    descriptionField, 
                    startField,
                    endField, 
                    time,
                    price
                );
                System.out.println("Offre de service bien créé");
                //print info newoffer
                //System.out.println("id: " + newOffer.getId() + " title: " + newOffer.getTitle() + " description: " + newOffer.getDescription() + " start: " + newOffer.getStart() + " end: " + newOffer.getEnd() + " time: " + newOffer.getTime() + " price: " + newOffer.getPrice() + " nb recurrence: " + newOffer.getRecurrency() + " supplier mail: " + newOffer.getSupplierMail() + " est pris: " + newOffer.getEstPris());
                skeleton_controller.loadServiceOfferPage(newOffer);

            } catch (Exception e) {
                System.out.println("Veuillez remplir tous les champs requis. Recommencez !");
                System.err.println(e);
                return;
            }
        }

    @FXML public void cancel() {
        System.out.println("cancel");
        skeleton_controller.loadServicePage();
    }
}
