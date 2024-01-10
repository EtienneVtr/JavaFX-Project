package eu.telecomnancy.labfx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

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
    private String estPris;

    public EquipmentOffer(String owner_mail) {
        this.owner_mail = owner_mail;
        this.owner = new User(owner_mail);
        loadEquipmentFromDB();
    }

    public EquipmentOffer(String owner_mail, String name, String description){
        this.owner_mail = owner_mail;
        this.owner = new User(owner_mail);
        this.name = name;
        this.description = description;
        loadEquipmentFromDB();
    }

    public EquipmentOffer(String name, String description, String owner_mail, int quantity, LocalDate startAvailability, LocalDate endAvailability, int price) {
        this.owner_mail = owner_mail;
        this.owner = new User(owner_mail);
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.start_availability = startAvailability;
        this.end_availability = endAvailability;
        this.price = price;
    }
    

    
    public EquipmentOffer(User owner, String name, String description, int quantity, LocalDate start_availability, LocalDate end_availability, int price) {
        this.owner = owner;
        this.owner_mail = owner.getMail();
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.start_availability = start_availability;
        this.end_availability = end_availability;
        this.price = price;
        this.estPris = null;
        createNewOffer();
    }


    public void createNewOffer() {
              System.out.println("Début de la recherche des offres.création");
        String sql = "INSERT INTO equipement (owner_mail, name, description, quantity, start_availability, end_availability, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
    
            pstmt.setString(1, this.owner.getMail());
            pstmt.setString(2, this.name);
            pstmt.setString(3, this.description);
            pstmt.setInt(4, this.quantity);
            pstmt.setString(5, (this.start_availability != null) ? this.start_availability.toString() : null);
            pstmt.setString(6, (this.end_availability != null) ? this.end_availability.toString() : null);
            pstmt.setInt(7, this.price);
            
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
        } finally {
            System.out.println("Fin de la recherche des offres création.");
        }
    }

    public void loadEquipmentById(int offerId) {
        String sql = "SELECT * FROM equipement WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, offerId);
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
    
    
    private void loadEquipmentFromDB() {
        String sql = "SELECT * FROM equipement WHERE owner_mail = ? AND name = ? AND description = ?";
    
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, this.owner.getMail());
            pstmt.setString(2, this.name);
            pstmt.setString(3, this.description);
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

    public boolean reserveOffer(EquipmentOffer offer, String currentUserEmail){
        Connection conn = null;
        try {
            conn = DataBase.getConnection();
            conn.setAutoCommit(false); // Démarrer une transaction
    
            // Vérifier si l'offre est déjà réservée
            String sql = "SELECT estPris FROM equipement WHERE id = ?";
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
            if (!updateFlorains(conn, currentUserEmail, offer.getMail(), offer.getPrice())) {
                conn.rollback();
                return false;
            }
    
            // Réserver l'offre
            sql = "UPDATE equipement SET estPris = ? WHERE id = ?";
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


    public static List<EquipmentOffer> searchOffers(User currentUser, String keywords, LocalDate begin, LocalDate end) {
        System.out.println("Début de la recherche des offres.");
        List<EquipmentOffer> offers = new ArrayList<>();
        String sql = "SELECT owner_mail, id, name, description, quantity, start_availability, end_availability, price FROM equipement WHERE " +
                     "estPris IS NULL AND " +
                     "name LIKE ? AND " +
                     "(? IS NULL OR start_availability >= ?) AND " +
                     "(? IS NULL OR end_availability <= ?)";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, "%" + keywords + "%");
            pstmt.setString(2, begin != null ? begin.toString() : null);
            pstmt.setString(3, end != null ? end.toString() : null);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate startAvailability = rs.getString("start_availability") != null ? LocalDate.parse(rs.getString("start_availability")) : null;
                    LocalDate endAvailability = rs.getString("end_availability") != null ? LocalDate.parse(rs.getString("end_availability")) : null;
                    
                    EquipmentOffer offer = new EquipmentOffer(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("owner_mail"),
                        rs.getInt("quantity"),
                        startAvailability,
                        endAvailability,
                        rs.getInt("price")
                    );
                    offers.add(offer);
                    System.out.println("Offre trouvée: " + offer.getName() + " " + offer.getDescription() + " " + offer.getMail() + " " + offer.getQuantity() + " " + offer.getStartAvailability() + " " + offer.getEndAvaibility() + " " + offer.getPrice());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Fin de la recherche des offres.");
        }
        //print all offers
        for(EquipmentOffer offer : offers){
            System.out.println("Offre trouvée: " + offer.getName() + " " + offer.getDescription() + " " + offer.getMail() + " " + offer.getQuantity() + " " + offer.getStartAvailability() + " " + offer.getEndAvaibility() + " " + offer.getPrice());
        }
        return offers;
    }
    
    
    

    public String getMail(){
        return owner_mail;
    }

    public User getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public void setName(String name) {
        this.name = name;
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
        if(start_availability == null){
            return "À définir";
        }else{
            return start_availability.toString();
        }
    }

    public void setStartAvailability(LocalDate begin){
        this.start_availability = begin;
    }

    public LocalDate getEndAvaibility(){
        return end_availability;
    }

    public String getEndAvailabilityStr(){
        if(end_availability == null){
            return "À définir";
        }else{
            return end_availability.toString();
        }
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

    public String getEstPris(){
        return estPris;
    }

    //set estPris
    public void setEstPris(String estPris) {
        this.estPris = estPris;
    }


}