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



public classe PrivateProfile{


    @FXML private TextField prenom;
    @FXML private TextField nom;
    @FXML private TextField pseudo;
    @FXML private TextField mail;
    @FXML private TextField localisation;
    @FXML private TextField telephone;
    @FXML private ImageView imageView;

    private User currentUser;


    public void setUserData(User user) {
        this.currentUser = user;
    }
    @FXML
    public void initileze() {
        prenom       = currentUser.getPrenom();
        nom          = currentUser.getNom();
        pseudo       = currentUser.getPseudo();
        mail         = currentUser.getMail();
        localisation = currentUser.getLocalisation();
        telephone    = currentUser.getTelephone();
        imageView    = currentUser.getPhotoProfil();
    }

    



}
