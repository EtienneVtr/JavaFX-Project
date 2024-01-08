package eu.telecomnancy.labfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import javafx.fxml.FXMLLoader;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("JavaFx Demo");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/Main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
