package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ServiceController {

    @FXML private Button ButtonCreate;
    @FXML private Label State;

    private SkeletonController skeleton_controller;
    private User currentUser = Main.getCurrentUser();

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }
    
    @FXML public void initialize() {
        if(currentUser.getEtatCompte().equals("sommeil")){
            ButtonCreate.setDisable(true);
            State.setVisible(true);
        }else{
            State.setVisible(false);
        }
    }

    @FXML public void handleCreateOffer() {
        System.out.println("Create offer");
        skeleton_controller.loadCreateServicePage();
    }

    @FXML public void handleConsultOffer() {
        System.out.println("Consult offer");
        skeleton_controller.loadListServiceOfferPage();
    }
}
