package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class CreateServiceController {

    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML private TextField title;
    @FXML private TextField description;
    @FXML private DatePicker date;
    @FXML private TextField time;
    @FXML private RadioButton yes;
    @FXML private RadioButton no;
    @FXML private TextField days_of_repetition;
    @FXML private TextField price;
    
    private ToggleGroup recurrenceGroup = new ToggleGroup();

    @FXML public void initialize() {
        // Assignation des RadioButton au ToggleGroup
        yes.setToggleGroup(recurrenceGroup);
        no.setToggleGroup(recurrenceGroup);

        // Définir "Non" comme valeur par défaut
        no.setSelected(true);
    }

    @FXML public void handleCreateOffer() {
        System.out.println("Create offer");
        skeleton_controller.loadServiceOfferPage();
    }

    @FXML public void cancel() {
        System.out.println("cancel");
        skeleton_controller.loadServicePage();
    }
}
