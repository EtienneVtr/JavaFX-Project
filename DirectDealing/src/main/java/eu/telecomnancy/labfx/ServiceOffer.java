
package eu.telecomnancy.labfx;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;

// Description: Classe représentant une offre de service. Elle contient un titre, une description, une date et une heure.
//              Elle peut être récurrente, auquel cas on lui ajoute un tableau de jours de la semaine où le service doit être réalisé.$

public class ServiceOffer {
    private int id;
    private User supplier;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private boolean isRecurrent;
    private String daysOfService; // Stocké comme une chaîne, par exemple "1,3,5"
    private int nbRecurrencingWeeks;
    private String supplier_mail;

    // Constructeur
    public ServiceOffer(String supplier_mail) {
        this.supplier_mail = supplier_mail;
        this.supplier = new User(supplier_mail);
        loadServiceFromDB();
    }

    private void loadServiceFromDB() {
        String sql = "SELECT * FROM service_offers WHERE supplier_mail = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, supplier_mail);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("id");
                this.title = rs.getString("title");
                this.description = rs.getString("description");
                String dateString = rs.getString("date");
                if (dateString != null && !dateString.isEmpty()) {
                    this.date = LocalDate.parse(dateString);
                    } else {
                    this.date = null;
                    }
                String timeString = rs.getString("time");
                if (timeString != null && !timeString.isEmpty()) {
                    this.time = LocalTime.parse(timeString);
                    } else {
                    this.time = null;
                    }
                this.isRecurrent = rs.getBoolean("is_recurrent");
                this.daysOfService = rs.getString("days_of_service");
                this.nbRecurrencingWeeks = rs.getInt("nb_recurrencing_weeks");
   
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        String sql = "UPDATE service_offers SET supplier_mail = ?, title = ?, description = ?, date = ?, time = ?, is_recurrent = ?, days_of_service = ?, nb_recurrencing_weeks = ? WHERE id = ?";
    
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, this.supplier.getMail());
            pstmt.setString(2, this.title);
            pstmt.setString(3, this.description);
            pstmt.setString(4, (this.date != null) ? this.date.toString() : null);
            pstmt.setString(5, (this.time != null) ? this.time.toString() : null);
            pstmt.setBoolean(6, this.isRecurrent);
            pstmt.setString(7, this.daysOfService);
            pstmt.setInt(8, this.nbRecurrencingWeeks);
            pstmt.setInt(9, this.id);
    
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public String getSupplierMail(){
        return supplier_mail;
    }

    public int getId(){
        return id;
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

    public void setRecurrent(boolean isRecurrent) {
        this.isRecurrent = isRecurrent;
    }

    public void setDaysOfService(String daysOfService) {
        this.daysOfService = daysOfService;
    }

    public void setNbRecurrencingWeeks(int nbRecurrencingWeeks) {
        this.nbRecurrencingWeeks = nbRecurrencingWeeks;
    }

    public boolean getIsRecurrent() {
        return isRecurrent;
    }

    public String getDaysOfService() {
        return daysOfService;
    }

    public int getNbRecurrencingWeeks() {
        return nbRecurrencingWeeks;
    }



}

