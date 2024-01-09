package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CreateEquipmentCrontroller {
    @FXML private TextField title;
    @FXML private TextField description;
    @FXML private TextField price;
    @FXML private TextField quantity;
    @FXML private DatePicker begin;
    @FXML private DatePicker end;

    @FXML public void handleCreateOffer() {
        System.out.println("Create offer");
    }

    @FXML public void cancel() {
        System.out.println("cancel");
    }
}
