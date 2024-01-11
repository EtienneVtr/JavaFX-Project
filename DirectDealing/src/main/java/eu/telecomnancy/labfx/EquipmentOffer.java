package eu.telecomnancy.labfx;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private String date_publication;
    private LocalDate book_begin;
    private LocalDate book_end;

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

    public EquipmentOffer(String owner_mail, String name, String description, String start, String estPris){
        this.owner_mail = owner_mail;
        this.owner = new User(owner_mail);
        this.name = name;
        this.description = description;
        this.start_availability = LocalDate.parse(start);
        this.estPris = estPris;
        loadEquipmentFromDBHome();
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
        String sql = "INSERT INTO equipement (owner_mail, name, description, quantity, start_availability, end_availability, price, date_publication) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
    
            pstmt.setString(1, this.owner.getMail());
            pstmt.setString(2, this.name);
            pstmt.setString(3, this.description);
            pstmt.setInt(4, this.quantity);
            pstmt.setString(5, (this.start_availability != null) ? this.start_availability.toString() : null);
            pstmt.setString(6, (this.end_availability != null) ? this.end_availability.toString() : null);
            pstmt.setInt(7, this.price);
            pstmt.setString(8, LocalDate.now().toString());
            
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
                this.date_publication = rs.getString("date_publication");
                this.estPris = rs.getString("estPris");
                String book_beginString = rs.getString("book_begin");
                if (book_beginString != null && !book_beginString.isEmpty()) {
                    this.book_begin = LocalDate.parse(book_beginString);
                    } else {
                    this.book_begin = null;
                    }
                String book_endString = rs.getString("book_end");
                if (book_endString != null && !book_endString.isEmpty()) {
                    this.book_end = LocalDate.parse(book_endString);
                    } else {
                    this.book_end = null;
                    }
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
                this.date_publication = rs.getString("date_publication");
                this.estPris = rs.getString("estPris");
                String book_beginString = rs.getString("book_begin");
                if (book_beginString != null && !book_beginString.isEmpty()) {
                    this.book_begin = LocalDate.parse(book_beginString);
                    } else {
                    this.book_begin = null;
                    }
                String book_endString = rs.getString("book_end");
                if (book_endString != null && !book_endString.isEmpty()) {
                    this.book_end = LocalDate.parse(book_endString);
                    } else {
                    this.book_end = null;
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadEquipmentFromDBHome() {
        String sql = "SELECT * FROM equipement WHERE owner_mail = ? AND name = ? AND description = ? AND start_availability = ? AND estPris IS NULL";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, this.owner.getMail());
            pstmt.setString(2, this.name);
            pstmt.setString(3, this.description);
            pstmt.setString(4, this.start_availability.toString());
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
                this.date_publication = rs.getString("date_publication");
                this.estPris = rs.getString("estPris");
                String book_beginString = rs.getString("book_begin");
                if (book_beginString != null && !book_beginString.isEmpty()) {
                    this.book_begin = LocalDate.parse(book_beginString);
                    } else {
                    this.book_begin = null;
                    }
                String book_endString = rs.getString("book_end");
                if (book_endString != null && !book_endString.isEmpty()) {
                    this.book_end = LocalDate.parse(book_endString);
                    } else {
                    this.book_end = null;
                    }
                
                // Afffichage du résultat :
                System.out.println("Offre trouvée: " + this.name + " " + this.description + " " + this.owner_mail + " " + this.quantity + " " + this.start_availability + " " + this.end_availability + " " + this.price);
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

    public boolean reserveOffer(EquipmentOffer offer, String currentUserEmail, LocalDate begin, LocalDate end){
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

            // Mettre à jour les dates de réservation
            sql = "UPDATE equipement SET book_begin = ?, book_end = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, begin.toString());
                pstmt.setString(2, end.toString());
                pstmt.setInt(3, offer.getId());
                pstmt.executeUpdate();
            }
            this.book_begin = begin;
            this.book_end = end;
    
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

    public static List<EquipmentOffer> searchOffers(User currentUser, String keywords, LocalDate begin, LocalDate end, Integer minPrice, Integer maxPrice, double radius) {
        System.out.println("Début de la recherche des offres avec un rayon de " + radius + " km et les mots clés " + keywords + " et les dates " + begin + " " + end + " et les prix " + minPrice + " " + maxPrice);
    
        List<EquipmentOffer> offers = new ArrayList<>();
        String sql = "SELECT owner_mail, id, name, description, quantity, start_availability, end_availability, price FROM equipement WHERE estPris IS NULL";

        if (!keywords.isEmpty()) {
            sql += " AND name LIKE ?";
        }

        if (begin != null && end != null) {
            // Les offres doivent être disponibles pour toute la période demandée
            sql += " AND (start_availability <= ? AND end_availability >= ?)";
        
        } else if (begin != null) {
            // Les offres doivent commencer au plus tard à la date de début
            sql += " AND (start_availability <= ?)";
        } else if (end != null) {
            // Les offres doivent se terminer au plus tôt à la date de fin
            sql += " AND (end_availability >= ?)";
        }

        // Logique pour le prix
        if (minPrice != null) {
            sql += " AND price >= '?' ";
        }
        if (maxPrice != null) {
            sql += " AND price <= ?";
        }

        System.out.println(sql);
    
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            int paramIndex = 1;
            if (!keywords.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + keywords + "%");
            }
            if (begin != null) {
                pstmt.setString(paramIndex++, begin.toString());
            }
            if (end != null) {
                pstmt.setString(paramIndex++, end.toString());
            }
            if (minPrice != null) {
                pstmt.setInt(paramIndex++, minPrice);
            }
            if (maxPrice != null) {
                pstmt.setInt(paramIndex++, maxPrice);
            }

    
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Filtrer les offres par rayon
        return offers.stream()
            .filter(offer -> isWithinRadius(currentUser, offer.getOwner(), radius))
            .collect(Collectors.toList());
    }
    
    
    

    private static boolean isWithinRadius(User currentUser, User offerOwner, double radius) {
        // Calcul de la distance entre l'utilisateur actuel et le propriétaire de l'offre
        double distance = currentUser.calculateDistanceTo(offerOwner);
    
        // Vérifiez si la distance est inférieure ou égale au rayon spécifié
        return distance <= radius;
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

    //get date_publication
    public String getDate_publication(){
        return date_publication;
    }

    //get début réservation
    public LocalDate getBook_begin(){
        return book_begin;
    }

    //get fin réservation
    public LocalDate getBook_end(){
        return book_end;
    }
}