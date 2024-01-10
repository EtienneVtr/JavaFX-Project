package eu.telecomnancy.labfx;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Planning {
    private User user;
    private ArrayList<CombinedOffer> my_offer; // les offres que j'ai publié et qui sont resrvées
    private ArrayList<CombinedOffer> my_demand; // les offres que d'autres ont publié et que j'ai réservé

    public Planning() {
        this.user = Main.getCurrentUser();
        this.my_offer = new ArrayList<>();
        this.my_demand = new ArrayList<>();
        update();
    }

    private void update() {
        ArrayList<EquipmentOffer> all_equipment = Main.getAllEquipment();
        ArrayList<ServiceOffer> all_service = Main.getAllService();

        String sql_equipment = "SELECT owner_mail, name, description, estPris FROM equipement WHERE owner_mail = ? OR estPris = ?";

        try (Connection conn = DataBase.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql_equipment)) {
            pstmt.setString(1, user.getMail());
            pstmt.setString(2, user.getMail());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                String ownerMail = rs.getString("owner_mail");
                String name = rs.getString("name");
                String description = rs.getString("description");
                EquipmentOffer equipment = new EquipmentOffer(ownerMail, name, description);
                
                if(rs.getString("estPris") != null && ownerMail.equals(user.getMail())){ // si l'offre est prise et que je suis le propriétaire alors je l'ajoute à mes offres
                    my_offer.add(new CombinedOffer(equipment));
                }else if (rs.getString("estPris").equals(user.getMail())){ // si l'offre est prise et que je suis le client alors je l'ajoute à mes demandes
                    my_demand.add(new CombinedOffer(equipment));                    
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
