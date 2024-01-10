package eu.telecomnancy.labfx;

public class MessagerieController {

    private SkeletonController skeleton_controller;

    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }
    

    public void handleSend() {
        System.out.println("Send");
    }
}
