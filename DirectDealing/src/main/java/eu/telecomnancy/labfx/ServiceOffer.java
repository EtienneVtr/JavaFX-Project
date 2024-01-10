package eu.telecomnancy.labfx;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private int price;
    private String estPris;

    // Constructeur
    public ServiceOffer(String supplier_mail) {
        this.supplier_mail = supplier_mail;
        this.supplier = new User(supplier_mail);
        loadServiceFromDB();
    }

    public ServiceOffer(String supplier_mail, String title, String description){
        this.supplier_mail = supplier_mail;
        this.supplier = new User(supplier_mail);
        this.title = title;
        this.description = description;
        loadServiceFromDB();
    }

    public ServiceOffer(User supplier, String title, String description, LocalDate date, LocalTime time, boolean isRecurrent, String daysOfService, int price) {
        this.supplier = supplier;
        this.supplier_mail = supplier.getMail();
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.isRecurrent = isRecurrent;
        this.daysOfService = daysOfService;
        this.price = price;
        this.estPris = null;
        if (isRecurrent) {
            String[] days = daysOfService.split(",");
            this.nbRecurrencingWeeks = days.length;
        } else {
            this.nbRecurrencingWeeks = 0;
        }
        createNewOffer();
    }




    public void createNewOffer(){
        String sql = "INSERT INTO service_offers (supplier_mail, title, description, date, time, is_recurrent, days_of_service, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
    
            pstmt.setString(1, this.supplier.getMail());
            pstmt.setString(2, this.title);
            pstmt.setString(3, this.description);
            pstmt.setString(4, (this.date != null) ? this.date.toString() : null);
            pstmt.setString(5, (this.time != null) ? this.time.toString() : null);
            pstmt.setBoolean(6, this.isRecurrent);
            pstmt.setString(7, this.daysOfService);
            pstmt.setInt(8, this.price);
    
            int affectedRows = pstmt.executeUpdate();
    
            // Vérifier si l'insertion a réussi et récupérer l'ID généré
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadServiceFromDB() {
        String sql = "SELECT * FROM service_offers WHERE supplier_mail = ? AND title = ? AND description = ?";

        try (Connection conn = DataBase.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, supplier_mail);
            pstmt.setString(2, title); // Assure-toi que la variable 'title' est définie et contient le titre à vérifier
            pstmt.setString(3, description); // Assure-toi que la variable 'description' est définie et contient la description à vérifier
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
                this.price = rs.getInt("price");
   
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        String sql = "UPDATE service_offers SET supplier_mail = ?, title = ?, description = ?, date = ?, time = ?, is_recurrent = ?, days_of_service = ?, price = ? WHERE id = ?";
    
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
    

    public boolean reserveOffer(ServiceOffer offer, String currentUserEmail) {
        Connection conn = null;
        try {
            conn = DataBase.getConnection();
            conn.setAutoCommit(false); // Démarrer une transaction
    
            // Vérifier si l'offre est déjà réservée
            String sql = "SELECT estPris FROM service_offers WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, offer.getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getString("estPris") != null) {
                    System.out.println("Cette offre a déjà été réservée");
                    conn.rollback();
                    return false;
                }
            }
    
            // Mettre à jour les florains
            if (!updateFlorains(conn, currentUserEmail, offer.getSupplierMail(), offer.getPrice())) {
                conn.rollback();
                return false;
            }
    
            // Réserver l'offre
            sql = "UPDATE service_offers SET estPris = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, currentUserEmail);
                pstmt.setInt(2, offer.getId());
                pstmt.executeUpdate();
            }
    
            conn.commit(); // Valider la transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la réservation de l'offre");
            if (conn != null) {
                try {
                    conn.rollback(); // Annuler la transaction en cas d'erreur
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restaurer le mode de commit automatique
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }

    public boolean updateFlorains(Connection conn, String buyerEmail, String sellerEmail, int price) throws SQLException {
        // Déduire les florains du compte de l'acheteur
        String sql = "UPDATE profil SET nb_florain = nb_florain - ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, price);
            pstmt.setString(2, buyerEmail);
            pstmt.executeUpdate();
        }
    
        // Ajouter les florains au compte du vendeur
        sql = "UPDATE profil SET nb_florain = nb_florain + ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, price);
            pstmt.setString(2, sellerEmail);
            pstmt.executeUpdate();
        }
    
        return true;
    }
    
    

    public String getSupplierMail(){
        return supplier_mail;
    }

    public User getSupplier(){
        return supplier;
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


    public boolean getIsRecurrent() {
        return isRecurrent;
    }

    public String getDaysOfService() {
        return daysOfService;
    }

    public int getRecurrency() {
        return nbRecurrencingWeeks;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setNbRecurrencingWeeks(String daysOfService) {
        if (this.isRecurrent) {
            String[] days = daysOfService.split(",");
            this.nbRecurrencingWeeks = days.length;
        } else {
            this.nbRecurrencingWeeks = 0;
        }
    }

    //get estPris
    public String getEstPris(){
        return estPris;
    }

    //set estPris
    public void setEstPris(String estPris) {
        this.estPris = estPris;
    }

}

