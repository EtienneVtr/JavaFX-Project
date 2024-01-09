package eu.telecomnancy.labfx;

import javafx.fxml.FXML;

public class EquipmentController {

    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
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
