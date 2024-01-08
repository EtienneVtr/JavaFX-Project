package eu.telecomnancy.labfx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String url = "jdbc:sqlite:chemin/vers/votre/base/de/données.db";
        String sql = "SELECT * FROM profil WHERE mail = ?";

        try (Connection conn = DriverManager.getConnection(url);
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
                this.dateInscription = rs.getDate("date_inscription").toLocalDate();
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
        this.etatCompte = etatCompte;
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
