package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
/* import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene; */
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
//import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;



public class PrivateProfileController{


    @FXML private TextField prenom;
    @FXML private TextField nom;
    @FXML private TextField pseudo;
    @FXML private TextField mail;
    @FXML private TextField localisation;
    @FXML private TextField phone;
    @FXML private ImageView imageView;

    private User currentUser;


    public void setUserData(User user) {
        this.currentUser = user;
    }
    @FXML
    public void initialize() {
        prenom.setText(currentUser.getPrenom());
        nom.setText(currentUser.getNom());
        pseudo.setText(currentUser.getPseudo());
        mail.setText(currentUser.getMail());
        localisation.setText(currentUser.getLocalisation());
        phone.setText(currentUser.getPhone());
    }

    



}
