package eu.telecomnancy.labfx;

import java.time.LocalDate;

// Description: Classe représentant une offre de matériel. Elle contient un nom, une description, une quantité, 
//              une date de début et de fin de disponibilité et un prix.
public class EquipmentOffer {
    private String name;
    private String description;
    private int quantity;
    private LocalDate start_availability;
    private LocalDate end_availability;
    private int price;

    public EquipmentOffer(String name, String description, int quantity, LocalDate start_availability, LocalDate end_availability, int price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.start_availability = start_availability;
        this.end_availability = end_availability;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getStartAvailability(){
        return start_availability;
    }

    public String getStartAvailabilityStr(){
        return start_availability.toString();
    }

    public void setStartAvailability(LocalDate begin){
        this.start_availability = begin;
    }

    public LocalDate getEndAvaibility(){
        return end_availability;
    }

    public String getEndAvailabilityStr(){
        return end_availability.toString();
    }

    public void setEndAvailability(LocalDate end){
        this.end_availability = end;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }
}
