package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.application.Platform;

public class MainController {
    @FXML
    public void somebodyClickOnMe() {
        System.out.println("Bye!");
        Platform.exit();
    }
}