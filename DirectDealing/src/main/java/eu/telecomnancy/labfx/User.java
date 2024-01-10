package eu.telecomnancy.labfx;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;


public class User {

    private int id;
    private String prenom;
    private String nom;
    private String pseudo;
    private String mail;
    private String phone;
    private String photoProfil;
    private String localisation;
    private LocalDate dateInscription;
    private String statusCompte;
    private String etatCompte;
    private int nbFlorain;
    private String historiqueFlorain;
    private Double note;

    
    public User(String mail) {
        this.mail = mail;
        loadUserFromDB();
    }

    private void loadUserFromDB() {
        String sql = "SELECT * FROM profil WHERE mail = ?";

        try (Connection conn = DataBase.getConnection(); // Utiliser DatabaseUtil pour obtenir la connexion
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, this.mail);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("id");
                this.prenom = rs.getString("prenom");
                this.nom = rs.getString("nom");
                this.pseudo = rs.getString("pseudo");
                this.phone = rs.getString("phone");
                this.photoProfil = rs.getString("photo_profil");
                this.localisation = rs.getString("localisation");
                String dateString = rs.getString("date_inscription");
                System.out.println("datestring" + dateString);
                if (dateString != null && !dateString.isEmpty()) {
                this.dateInscription = LocalDate.parse(dateString);
                } else {
                this.dateInscription = null; // ou une date par défaut si nécessaire
                }
                this.statusCompte = rs.getString("status_compte");
                this.etatCompte = rs.getString("etat_compte");
                this.nbFlorain = rs.getInt("nb_florain");
                this.historiqueFlorain = rs.getString("historique_florain");
                this.note = rs.getDouble("note");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void update() {
        String sql = "UPDATE profil SET prenom = ?, nom = ?, pseudo = ?, phone = ?, photo_profil = ?, localisation = ?, status_compte = ?, etat_compte = ?, nb_florain = ?, historique_florain = ?, note = ? WHERE mail = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, this.prenom);
            pstmt.setString(2, this.nom);
            pstmt.setString(3, this.pseudo);
            pstmt.setString(4, this.phone);
            pstmt.setString(5, this.photoProfil);
            pstmt.setString(6, this.localisation);
            pstmt.setString(7, this.statusCompte);
            pstmt.setString(8, this.etatCompte);
            pstmt.setInt(9, this.nbFlorain);
            pstmt.setString(10, this.historiqueFlorain);
            pstmt.setDouble(11, this.note);
            pstmt.setString(12, this.mail);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Getters et Setters
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public String setMail(String mail) {
        return this.mail;
    }
    
    public String getPseudo() {
        return pseudo;
    }
    
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPhotoProfil() {
        return photoProfil;
    }
    
    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }
    
    public String getLocalisation() {
        return localisation;
    }
    
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
    
    public LocalDate getDateInscription() {
        return dateInscription;
    }
    
    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }
    
    public String getStatusCompte() {
        return statusCompte;
    }
    
    public void setStatusCompte(String statusCompte) {
        this.statusCompte = statusCompte;
    }
    
    public String getEtatCompte() {
        return etatCompte;
    }
    
    public void setEtatCompte(String etatCompte) {
        if (etatCompte.equals("actif") || etatCompte.equals("sommeil")) {
            this.etatCompte = etatCompte;
        } else {
            throw new IllegalArgumentException("L'état du compte doit être 'actif' ou 'inactif'");
        }
    }
    
    public int getNbFlorain() {
        return nbFlorain;
    }
    
    public void setNbFlorain(int nbFlorain) {
        this.nbFlorain = nbFlorain;
    }
    
    public String getHistoriqueFlorain() {
        return historiqueFlorain;
    }
    
    public void setHistoriqueFlorain(String historiqueFlorain) {
        this.historiqueFlorain = historiqueFlorain;
    }
    
    public Double getNote() {
        return note;
    }
    
    public void setNote(Double note) {
        this.note = note;
    }
    
    

    
}
