package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.image.Image;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class PrivateProfileController {

    private SkeletonController skeleton_controller;
    private static User currentUser;
    @FXML private TextField pseudo;
    @FXML private TextField prenom;
    @FXML private TextField nom;
    @FXML private Label mail;
    @FXML private TextField phone;
    @FXML private TextField localisation;
    @FXML private Button toggleStateButton;
    @FXML private Label dateInscription;
    @FXML private Label nbFlorain;
    @FXML private Label note;
    @FXML private ImageView photoProfil;
    @FXML private String photoProfilPath;

    public void initialize(){
        System.out.println("Initialisation du profile privé");
        currentUser = Main.getCurrentUser();
        updateUIWithUserData();
        dateInscription.setText(currentUser.getDateInscription().toString());
        nbFlorain.setText(String.valueOf(currentUser.getNbFlorain()));
        mail.setText(currentUser.getMail());
        note.setText(String.valueOf(currentUser.getNote()));
        updateToggleButton();

        String cheminImageProfil = currentUser.getPhotoProfil();
        try {
            InputStream inputStream;
            if (cheminImageProfil == null) {
                System.out.println("L'utilisateur n'a pas de photo de profil");
                inputStream = getClass().getResourceAsStream("/eu/telecomnancy/labfx/images/kawai.png");
            } else {
                inputStream = new FileInputStream(cheminImageProfil);
            }
            Image image = new Image(inputStream);
            photoProfil.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Handle the exception, for example, by showing an error message or setting a default image
        }
    }  


    private void updateUIWithUserData() {
        if (currentUser != null) {
            pseudo.setText(currentUser.getPseudo());
            prenom.setText(currentUser.getPrenom());
            nom.setText(currentUser.getNom());
            phone.setText(currentUser.getPhone());
            localisation.setText(currentUser.getLocalisation());


        }
    }

    private void updateToggleButton() {
        if (currentUser != null) {
            toggleStateButton.setText(currentUser.getEtatCompte().equals("actif") ? "Passer en inactif" : "Passer en actif");
        }
    }
    
    @FXML
    private void handleSaveButtonAction() {
        if (currentUser != null) {
            System.out.println("Sauvegarde des données de l'utilisateur");
            currentUser.setPseudo(pseudo.getText());
            currentUser.setPrenom(prenom.getText());
            currentUser.setNom(nom.getText());
            currentUser.setPhone(phone.getText());
            currentUser.setLocalisation(localisation.getText());

            currentUser.update(); 
        }
    }

    @FXML
    private void onToggleStateAction() {
    if (currentUser != null) {
        String nouvelEtat = currentUser.getEtatCompte().equals("actif") ? "sommeil" : "actif";
        currentUser.setEtatCompte(nouvelEtat);
        toggleStateButton.setText(nouvelEtat.equals("actif") ? "Passer en sommeil" : "Passer en actif");
        currentUser.update(); 
        System.out.println(currentUser.getEtatCompte());
        }
    }

    @FXML
    private void onPhotoProfilClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionnez une Image de Profil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(photoProfil.getScene().getWindow());
        if (file != null) {
            currentUser.setPhotoProfil(file.getAbsolutePath());
            Image image = new Image("file:" + file.getAbsolutePath());
            photoProfil.setImage(image);
        }
    }

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }


}
