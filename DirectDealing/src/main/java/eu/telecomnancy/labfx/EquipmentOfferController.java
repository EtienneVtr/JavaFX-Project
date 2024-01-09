package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EquipmentOfferController {
    @FXML private Label title;
    @FXML private Label description;
    @FXML private Label quantity;
    @FXML private Label dates;
    @FXML private Label price;

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