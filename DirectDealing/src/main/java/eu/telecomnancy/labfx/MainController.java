package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;

public class MainController {

    

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private SkeletonController skeleton_controller;

    private static User currentUser;

    public void initialize(){
        System.out.println("Initialisation de la session");
        currentUser = Main.getCurrentUser();
    }

    // Fonction qui permet de charger la page de bienvenue
    public void loadWelcomePage() {
        System.out.println("Chargement de la page de bienvenue");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/WelcomePage.fxml"));
            Parent welcomePage = loader.load();

            // Ajouter le WelcomePage à la scène
            Main.getPrimaryStage().getScene().setRoot(welcomePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Fonction qui permet de charger la page d'inscription
    public void loadInscriptionPage() {
        try {
            System.out.println("Chargement de la page d'inscription");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/InscriptionPage.fxml"));
            Parent inscription = loader.load();

            InscriptionController inscriptionController = loader.getController();
            inscriptionController.setMainController(this);

            // Ajouter la page d'inscription à la scène
            Main.getPrimaryStage().getScene().setRoot(inscription);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Fonction qui charge le squellete de la page d'accueil
    public void loadSkeleton() {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/SkeletonPage.fxml"));
            Parent skeletonPage = loader.load();
            SkeletonController skeleton_controller = loader.getController();
            skeleton_controller.setMainController(this);
            Main.getPrimaryStage().getScene().setRoot(skeletonPage);
            setSkeletonController(skeleton_controller);

            skeleton_controller.loadMenuPage();
            skeleton_controller.loadProfilePage();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHomePage() {
        loadSkeleton();
        skeleton_controller.loadHomePage();
    }

    public void loadServicePage(){
        loadSkeleton();
        skeleton_controller.loadServicePage();
    }


    // Bouton qui charge la page d'incription
    @FXML
    public void handleInscription() throws IOException {
        loadInscriptionPage();
    }

    // Bouton qui charge la page de bienvenue
    @FXML
    public void handleWelcome() throws IOException {
        loadWelcomePage();
    }



    // Bouton qui tente la connexion
    @FXML
    private void handleConnexion() {
        String email = emailField.getText();
        String password = passwordField.getText();
        try (Connection conn = DataBase.getConnection()) {
            String sql = "SELECT * FROM profil WHERE mail = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Utilisateur trouvé, rediriger vers Dashboard.fxml
                    System.out.println("Utilisateur trouvé, Connexion réussie");

                    // Creation d'un objet User
                    User user = new User(email);
                    currentUser = user;
                    Main.setCurrentUser(currentUser);
                    System.out.println("Prenom: " + user.getPrenom() + " Nom: " + user.getNom() + " Pseudo: " + user.getPseudo() + " Mail: " + user.getMail() + " Phone: " + user.getPhone() + " Photo de profil: " + user.getPhotoProfil() + " Localisation: " + user.getLocalisation() + " Date d'inscription: " + user.getDateInscription() + " Status du compte: " + user.getStatusCompte() + " Etat du compte: " + user.getEtatCompte() + " Nombre de florain: " + user.getNbFlorain() + " Historique florain: " + user.getHistoriqueFlorain() + " Note: " + user.getNote());
                    // Charger la page d'accueil
                    loadHomePage();
                } else {
                    System.out.println("Identifiants incorrects");
                    // Afficher un message d'erreur
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des erreurs SQL
        }
    }

    //Fonction qui permet de définir le skeleton controller
    public void setSkeletonController(SkeletonController skeleton_controller) {
        this.skeleton_controller = skeleton_controller;
    }


    
}
