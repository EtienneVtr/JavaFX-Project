package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProfileController {

    private SkeletonController skeleton_controller;
    private static User currentUser;
    
    @FXML private Label labelPseudo;

    @FXML private Label labelSoldeFlorain;
    


    @FXML private ImageView photoProfil;

    public void initialize(){
        System.out.println("Initialisation du profile");
        currentUser = Main.getCurrentUser();
        String cheminImageProfil = currentUser.getPhotoProfil();
        //Chemin type : /Users/maxence/Downloads/chat.png
        if (cheminImageProfil == null) {
            System.out.println("L'utilisateur n'a pas de photo de profil");
            cheminImageProfil = "src/main/resources/eu/telecomnancy/labfx/images/default_profile.png";
        }
        Image image = new Image("file:" + cheminImageProfil);
        photoProfil.setImage(image);
        updateProfileInfo(currentUser);

    }
    

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }


    @FXML public void handleDeconnexion(){
        System.out.println("Deconnexion de la session");
        skeleton_controller.main_controller.loadWelcomePage();
    }
    
    @FXML
    public void updateProfileInfo(User user) {
        labelPseudo.setText(user.getPseudo());
        labelSoldeFlorain.setText(String.valueOf(user.getNbFlorain()));
    }

    @FXML 
    public void handlePrivateProfile() {
        System.out.println("Affichage du profil privé");
        skeleton_controller.loadPrivateProfile();
    }
}
