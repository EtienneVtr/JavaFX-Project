package eu.telecomnancy.labfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import javafx.fxml.FXMLLoader;

public class Main extends Application {

    private static Stage currentStage;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Main.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Inscription");
            stage.setScene(new Scene(root, 1000, 560)); 
            Main.setPreviousStage(stage);

            stage.show();
    }


    public static Stage getPreviousStage() {
        return currentStage;
    }

    public static void setPreviousStage(Stage stage) {
        currentStage = stage;
    }
}
