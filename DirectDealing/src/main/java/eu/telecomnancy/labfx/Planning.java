package eu.telecomnancy.labfx;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Planning {
    private User user;
    private ArrayList<CombinedOffer> my_offer; // Les offres que j'ai publiées et qui sont réservées
    private ArrayList<CombinedOffer> my_demand; // Les offres que d'autres ont publiées et que j'ai réservées


    public Planning(User user) {
        this.user = user;
        this.my_offer = new ArrayList<>();
        this.my_demand = new ArrayList<>();
        update();
    }

    private void update() {
        String sql_equipment = "SELECT owner_mail, name, description, estPris FROM equipement WHERE owner_mail = ? OR estPris = ?";

        try (Connection conn = DataBase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql_equipment)) {
            pstmt.setString(1, user.getMail());
            pstmt.setString(2, user.getMail());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("estPris") == null) {
                    continue;
                }

                String ownerMail = rs.getString("owner_mail");
                String name = rs.getString("name");
                String description = rs.getString("description");
                EquipmentOffer equipment = new EquipmentOffer(ownerMail, name, description);

                if (rs.getString("estPris") != null && ownerMail.equals(user.getMail())) {
                    // Si l'offre est prise et que je suis le propriétaire, je l'ajoute à mes offres
                    my_offer.add(new CombinedOffer(equipment));
                } else if (rs.getString("estPris").equals(user.getMail())) {
                    // Si l'offre est prise et que je suis le client, je l'ajoute à mes demandes
                    my_demand.add(new CombinedOffer(equipment));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sql_service = "SELECT supplier_mail, title, description, estPris FROM service_offers WHERE supplier_mail = ? OR estPris = ?";

        try (Connection conn = DataBase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql_service)) {
            pstmt.setString(1, user.getMail());
            pstmt.setString(2, user.getMail());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("estPris") == null) {
                    continue;
                }

                String supplierMail = rs.getString("supplier_mail");
                String title = rs.getString("title");
                String description = rs.getString("description");
                ServiceOffer service = new ServiceOffer(supplierMail, title, description);

                if (rs.getString("estPris") != null && supplierMail.equals(user.getMail())) {
                    // Si l'offre est prise et que je suis le propriétaire, je l'ajoute à mes offres
                    my_offer.add(new CombinedOffer(service));
                } else if (rs.getString("estPris").equals(user.getMail())) {
                    // Si l'offre est prise et que je suis le client, je l'ajoute à mes demandes
                    my_demand.add(new CombinedOffer(service));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<CombinedOffer> getMyOffer() {
        return my_offer;
    }

    public ArrayList<CombinedOffer> getMyDemand() {
        return my_demand;
    }



}

