package eu.telecomnancy.labfx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

// Description: Classe représentant une offre de matériel. Elle contient un nom, une description, une quantité, 
//              une date de début et de fin de disponibilité et un prix.
public class EquipmentOffer {
    private User owner;
    private int id;
    private String name;
    private String description;
    private int quantity;
    private LocalDate start_availability;
    private LocalDate end_availability;
    private int price;
    private String owner_mail;

    public EquipmentOffer(String owner_mail) {
        this.owner_mail = owner_mail;
        this.owner = new User(owner_mail);
        loadEquipmentFromDB();

    }
    
    private void loadEquipmentFromDB() {
        String sql = "SELECT * FROM equipement WHERE owner_mail = ?";
    
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, this.owner.getMail());
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                this.name = rs.getString("name");
                this.id = rs.getInt("id");
                this.description = rs.getString("description");
                this.quantity = rs.getInt("quantity");
                String start_availabilityString = rs.getString("start_availability");
                if (start_availabilityString != null && !start_availabilityString.isEmpty()) {
                    this.start_availability = LocalDate.parse(start_availabilityString);
                    } else {
                    this.start_availability = null;
                    }
                String end_availabilityString = rs.getString("end_availability");
                if (end_availabilityString != null && !end_availabilityString.isEmpty()) {
                    this.end_availability = LocalDate.parse(end_availabilityString);
                    } else {
                    this.end_availability = null;
                    }
                this.price = rs.getInt("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update() {
        String sql = "UPDATE equipement SET owner_mail = ?, name = ?, description = ?, quantity = ?, start_availability = ?, end_availability = ?, price = ? WHERE id = ?";
    
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
            pstmt.setString(1, this.owner.getMail());
            pstmt.setString(2, this.name);
            pstmt.setString(3, this.description);
            pstmt.setInt(4, this.quantity);
            pstmt.setString(5, (this.start_availability != null) ? this.start_availability.toString() : null);
            pstmt.setString(6, (this.end_availability != null) ? this.end_availability.toString() : null);
            pstmt.setInt(7, this.price);
            pstmt.setInt(8, this.id);
    
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
