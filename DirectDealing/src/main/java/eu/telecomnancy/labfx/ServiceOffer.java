package eu.telecomnancy.labfx;

import java.time.LocalDate;
import java.time.LocalTime;

// Description: Classe représentant une offre de service. Elle contient un titre, une description, une date et une heure.
//              Elle peut être récurrente, auquel cas on lui ajoute un tableau de jours de la semaine où le service doit être réalisé.
public class ServiceOffer {
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private boolean is_recurrent;
    private int[] days_of_service; // ici on ajoutera les jours où le service doit être réalisés lors d'une récurrence.
                                   // On notera 1 = lundi, 2 = mardi, etc.
    private int nb_recurrencing_weeks;

    public ServiceOffer(String title, String description, LocalDate date, LocalTime time){
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.is_recurrent = false;
    }

    public ServiceOffer(String title, String description, LocalDate date, LocalTime time, int[] days_of_service, int nb_recurrencing_weeks){
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.is_recurrent = true;
        this.days_of_service = days_of_service;
        this.nb_recurrencing_weeks = nb_recurrencing_weeks;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public LocalDate getDate(){
        return date;
    }

    public String getDateStr(){
        return date.toString();
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalTime getTime(){
        return time;
    }

    public String getTimeStr(){
        return time.toString();
    }

    public void setTime(LocalTime time){
        this.time = time;
    }

    public boolean getIsRecurrent(){
        return is_recurrent;
    }

    public int[] getDaysOfService() {
        return this.days_of_service;
    }

    public void setDaysOfService(int[] days_of_service) {
        this.days_of_service = days_of_service;
    }

    public int getNbRecurrencingWeeks() {
        return this.nb_recurrencing_weeks;
    }

    public void setNbRecurrencingWeeks(int nb_recurrencing_weeks) {
        this.nb_recurrencing_weeks = nb_recurrencing_weeks;
    }
}
