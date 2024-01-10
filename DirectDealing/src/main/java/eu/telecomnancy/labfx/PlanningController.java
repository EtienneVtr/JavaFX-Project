package eu.telecomnancy.labfx;

import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;

public class PlanningController {
        
        
    @FXML
    private CalendarView planningCalendar;

    private SkeletonController skeleton_controller;


    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }
}
