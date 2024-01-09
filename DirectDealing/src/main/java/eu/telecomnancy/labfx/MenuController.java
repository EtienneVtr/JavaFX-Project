package eu.telecomnancy.labfx;

import javafx.fxml.FXML;

public class MenuController {
    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML public void handleService() {
        System.out.println("Service");
        skeleton_controller.loadServicePage();
    }

    @FXML public void handleHome() {
        System.out.println("Home");
        skeleton_controller.loadHomePage();
    }

    @FXML public void handleEquipment() {
        System.out.println("Equipment");
        skeleton_controller.loadEquipmentPage();
    }
}
