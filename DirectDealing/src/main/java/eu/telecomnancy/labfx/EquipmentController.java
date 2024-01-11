package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EquipmentController {

    private SkeletonController skeleton_controller;
    private User currentUser = Main.getCurrentUser();

    @FXML private Button ButtonCreate;
    @FXML private Label State;

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
        skeleton_controller.loadCreateEquipmentPage();
    }

    @FXML public void handleConsultOffer() {
        System.out.println("Consult offer");
        skeleton_controller.loadListEquipmentOfferPage();
    }
}
