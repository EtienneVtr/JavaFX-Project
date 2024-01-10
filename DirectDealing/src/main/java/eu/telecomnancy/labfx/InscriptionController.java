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



//import javafx.application.Platform;

public class InscriptionController {

    private MainController mainController;

    @FXML private TextField prenom;
    @FXML private TextField nom;
    @FXML private TextField pseudo;
    @FXML private TextField mail;
    @FXML private TextField password;
    @FXML private TextField password2;
    @FXML private TextField localisation;
    @FXML private TextField telephone;
    @FXML private ImageView imageView;

    private String imagePath;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void handleInscription() throws IOException {
        String prenomValue = prenom.getText();
        String nomValue = nom.getText();
        String pseudoValue = pseudo.getText();
        String mailValue = mail.getText();
        String passwordValue = password.getText();
        String password2Value = password2.getText();
        String localisationValue = localisation.getText();
        String phoneValue = telephone.getText();
    
        // Vérifiez si les champs obligatoires sont remplis
        if (prenomValue.isEmpty() || nomValue.isEmpty() || pseudoValue.isEmpty() || 
            mailValue.isEmpty() || passwordValue.isEmpty() || localisationValue.isEmpty()) {
            System.out.println("Tous les champs obligatoires doivent être remplis");
            return;
        }
    
        if (!passwordValue.equals(password2Value)) {
            System.out.println("Les mots de passe ne correspondent pas");
            return;
        }
        
        try (Connection conn = DataBase.getConnection()) {
            if (userExists(conn, pseudoValue, mailValue)) {
                System.out.println("Un utilisateur avec ce pseudo ou ce mail existe déjà");
                return;
            }
    
            String sql = "INSERT INTO profil (prenom, nom, pseudo, mail, password, phone, localisation, date_inscription, nb_florain, photo_profil, etat_compte) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 100, ?, 'actif')";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, prenomValue);
                pstmt.setString(2, nomValue);
                pstmt.setString(3, pseudoValue);
                pstmt.setString(4, mailValue);
                pstmt.setString(5, passwordValue);
                pstmt.setString(6, phoneValue.isEmpty() ? null : phoneValue); // Téléphone non obligatoire
                pstmt.setString(7, localisationValue);
                pstmt.setString(8, LocalDate.now().toString());
                pstmt.setString(9, imagePath); // Photo de profil non obligatoire
    
                pstmt.executeUpdate();
                System.out.println("Utilisateur créé");
                // Reste du code pour la redirection
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des erreurs SQL
        }
    
    
        // Redirection vers WelcomePage.fxml
        mainController.loadWelcomePage();
    }
    
    

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );
    
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
    
            // Stocker le chemin d'accès de l'image
            imagePath = selectedFile.getAbsolutePath();
        }
    }


    private boolean userExists(Connection conn, String pseudo, String mail) throws SQLException {
    String sql = "SELECT * FROM profil WHERE pseudo = ? OR mail = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, pseudo);
        pstmt.setString(2, mail);
        ResultSet rs = pstmt.executeQuery();
        return rs.next(); // Retourne vrai si un enregistrement existe
        }
    }


    // Bouton qui charge la page de bienvenue
    @FXML
    public void handleRetour() throws IOException {
        mainController.loadWelcomePage();
    }

}




