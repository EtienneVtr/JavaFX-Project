package eu.telecomnancy.labfx;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
            // Vérifiez si la table "profil" existe et créez-la si nécessaire
            String sql = "CREATE TABLE IF NOT EXISTS profil (" +
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
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }
}
