package eu.telecomnancy.labfx;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBase {

    private static final String DB_FOLDER = "BD";
    private static final String DB_NAME = "DirectDealing.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_FOLDER + File.separator + DB_NAME;

    // Méthode pour obtenir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        File dbFolder = new File(DB_FOLDER);
        if (!dbFolder.exists()) {
            dbFolder.mkdir();
        }
        return DriverManager.getConnection(DB_URL);
    }

    // Méthode pour initialiser la base de données
    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Création de la table "profil" si elle n'existe pas
            String sqlProfil = "CREATE TABLE IF NOT EXISTS profil (" +
                             "id INTEGER PRIMARY KEY, " +
                             "prenom TEXT NOT NULL, " +
                             "nom TEXT NOT NULL, " +
                             "pseudo TEXT UNIQUE NOT NULL, " +
                             "mail TEXT UNIQUE NOT NULL, " +
                             "phone TEXT, " +
                             "password TEXT NOT NULL, " +
                             "photo_profil TEXT, " +
                             "localisation TEXT NOT NULL, " +
                             "date_inscription TEXT, " +
                             "status_compte TEXT CHECK(status_compte IN ('particulier', 'professionnel')), " +
                             "etat_compte TEXT CHECK(etat_compte IN ('sommeil', 'actif')), " +
                             "nb_florain INTEGER, " +
                             "historique_florain TEXT, " +
                             "note REAL)";
            stmt.execute(sqlProfil);

            // Création de la table "equipement" si elle n'existe pas
            String sqlEquipement = "CREATE TABLE IF NOT EXISTS equipement (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "owner_mail TEXT NOT NULL, " +
                            "name TEXT NOT NULL, " +
                            "description TEXT, " +
                            "quantity INTEGER NOT NULL, " +
                            "start_availability TEXT, " +
                            "end_availability TEXT, " +
                            "price INTEGER NOT NULL, " +
                            "estPris TEXT, " +
                            "date_publication TEXT, " +
                            "FOREIGN KEY (owner_mail) REFERENCES profil (mail), " +
                            "FOREIGN KEY (estPris) REFERENCES profil (mail))";
            stmt.execute(sqlEquipement);


            // Création de la table "service_offers" si elle n'existe pas
            String sqlServiceOffers = "CREATE TABLE IF NOT EXISTS service_offers (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "supplier_mail TEXT NOT NULL, " +
                            "title TEXT NOT NULL, " +
                            "description TEXT, " +
                            "date TEXT, " +
                            "time TEXT, " +
                            "is_recurrent BOOLEAN, " +
                            "days_of_service TEXT, " +
                            "price INTEGER, " +
                            "estPris TEXT, " +
                            "date_publication TEXT, " +
                            "FOREIGN KEY (supplier_mail) REFERENCES profil (mail), " +
                            "FOREIGN KEY (estPris) REFERENCES profil (mail))";
            stmt.execute(sqlServiceOffers);
            
            

            // Création de la table "Conversations" si elle n'existe pas
            String sqlConversations = "CREATE TABLE IF NOT EXISTS conversations (" +
                                      "conversation_id INTEGER PRIMARY KEY," +
                                      "participant1_id INTEGER NOT NULL," +
                                      "participant2_id INTEGER NOT NULL);";
            stmt.execute(sqlConversations);

            // Création de la table "Messages" si elle n'existe pas
            String sqlMessages = "CREATE TABLE IF NOT EXISTS messages (" +
                                 "message_id INTEGER PRIMARY KEY," +
                                 "conversation_id INTEGER NOT NULL," +
                                 "sender_id INTEGER NOT NULL," +
                                 "message_text TEXT NOT NULL," +
                                 "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                                 "FOREIGN KEY (conversation_id) REFERENCES conversations(conversation_id));";
            stmt.execute(sqlMessages);



        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }

}