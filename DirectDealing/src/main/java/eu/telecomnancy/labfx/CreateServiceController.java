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

    private User currentUser;

    @FXML
    public void initialize() {
        System.out.println("EquipmentController initialize");
        currentUser = Main.getCurrentUser();
    } 
    

    @FXML private TextField title;
    @FXML private TextField description;
    @FXML private DatePicker date;
    @FXML private TextField time;
    @FXML private RadioButton yes;
    @FXML private RadioButton no;
    @FXML private TextField days_of_repetition;
    @FXML private TextField price;
    
    @FXML public void handleCreateOffer() {
            System.out.println("Create service");
            String titleField = title.getText();
            String descriptionField = description.getText();
            String priceStr = price.getText();
            String repetitionDay = days_of_repetition.getText();

            boolean isRecurrent = yes.isSelected();
            
            if (titleField.isEmpty() || descriptionField.isEmpty() || priceStr.isEmpty()) {
                System.out.println("Veuillez remplir tous les champs requis.");
                return;
            }
            
            try{

                int price = Integer.parseInt(priceStr);
                LocalDate dateField = date.getValue();
                LocalTime time = LocalTime.parse(this.time.getText()); //transform textfield to local time
                //transform textfield to local time
                
                ServiceOffer newOffer = new ServiceOffer(
                    currentUser, 
                    titleField, 
                    descriptionField, 
                    dateField, 
                    time, 
                    isRecurrent, 
                    repetitionDay, 
                    price
                );
                System.out.println("Offre de service bien créé");
                skeleton_controller.loadServicePage();

            } catch (Exception e) {
                System.out.println("Veuillez remplir tous les champs requis.");
                return;
            }
        }

    @FXML public void cancel() {
        System.out.println("cancel");
        skeleton_controller.loadServicePage();
    }
}
