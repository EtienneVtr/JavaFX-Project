package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrivateProfileController {

    private SkeletonController skeleton_controller;
    private static User currentUser;
    @FXML private TextField pseudo;
    @FXML private TextField prenom;
    @FXML private TextField nom;
    @FXML private TextField mail;
    @FXML private TextField phone;
    @FXML private TextField localisation;

    public void initialize(){
        System.out.println("Initialisation du profile privé");
        currentUser = Main.getCurrentUser();
        updateUIWithUserData();
    }    

    private void updateUIWithUserData() {
        if (currentUser != null) {
            pseudo.setText(currentUser.getPseudo());
            prenom.setText(currentUser.getPrenom());
            nom.setText(currentUser.getNom());
            mail.setText(currentUser.getMail());
            phone.setText(currentUser.getPhone());
            localisation.setText(currentUser.getLocalisation());
        }
    }
    
    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML public void handlePrivateProfile(){
        System.out.println("Deconnexion de la session");
        skeleton_controller.main_controller.loadPrivateProfile();
    }
}
