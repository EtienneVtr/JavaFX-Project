package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class ConsultServiceController {
    @FXML private TextField keywords;
    @FXML private DatePicker begin;
    @FXML private DatePicker end;
    @FXML private Slider radius;
    @FXML private ListView<HBox> results;

    @FXML public void handleSearch() {
        System.out.println("Search");
    }

    @FXML public void handleReset() {
        System.out.println("Reset");
    }
}
