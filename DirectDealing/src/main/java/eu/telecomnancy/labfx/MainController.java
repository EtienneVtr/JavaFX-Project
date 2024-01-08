package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.application.Platform;

public class MainController {

private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @FXML
    public void handleRetour() {
        Platform.exit();
    }

    @FXML
    public void handleInscription() {
    }

    @FXML
    public void chooseImage() {
    
    }

}