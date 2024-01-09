package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;

public class CreateServiceController {
    @FXML private TextField title;
    @FXML private TextField description;
    @FXML private DatePicker date;
    @FXML private TextField time;
    @FXML private RadioButton yes;
    @FXML private RadioButton no;
    @FXML private TextField days_of_repetition;
    @FXML private TextField price;
    
    @FXML public void handleCreateOffer() {
        System.out.println("Create offer");
    }

    @FXML public void cancel() {
        System.out.println("cancel");
    }
}
