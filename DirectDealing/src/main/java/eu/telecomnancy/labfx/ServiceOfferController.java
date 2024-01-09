package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServiceOfferController {
    @FXML private Label title;
    @FXML private Label description;
    @FXML private Label date;
    @FXML private Label recurrency;
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
