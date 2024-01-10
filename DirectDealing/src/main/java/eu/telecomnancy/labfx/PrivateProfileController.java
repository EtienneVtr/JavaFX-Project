package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class PrivateProfileController {

    private SkeletonController skeleton_controller;
    private static User currentUser;
    @FXML private TextField pseudo;
    @FXML private TextField prenom;
    @FXML private TextField nom;
    @FXML private TextField mail;
    @FXML private TextField phone;
    @FXML private TextField localisation;
    @FXML private Button toggleStateButton;
    @FXML private Label dateInscription;
    @FXML private Label nbFlorain;
    @FXML private Label note;

    public void initialize(){
        System.out.println("Initialisation du profile privé");
        currentUser = Main.getCurrentUser();
        updateUIWithUserData();
        dateInscription.setText(currentUser.getDateInscription().toString());
        nbFlorain.setText(String.valueOf(currentUser.getNbFlorain()));
        note.setText(String.valueOf(currentUser.getNote()));
        updateToggleButton();
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
            currentUser.setMail(mail.getText());
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

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }


}
