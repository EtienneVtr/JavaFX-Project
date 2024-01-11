package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;


public class MapController {

    private SkeletonController skeleton_controller;

    private Map<String, Integer> offerCountByCity = new HashMap<>();


    public void setSkeletonController(SkeletonController skeleton_controller){
        this.skeleton_controller = skeleton_controller;
    }

    @FXML
    private Pane mapPane;
    private Image mapImage;

    public void initialize() {
        // Initialiser mapImage avec l'image de la carte
        mapImage = new Image(getClass().getResourceAsStream("/eu/telecomnancy/labfx/images/france.png"));

        drawMap();
        drawOffers();
    }

    private void drawMap() {
        // Assurez-vous que l'image a été chargée correctement
        if (mapImage != null) {
            ImageView mapImageView = new ImageView(mapImage);
            mapImageView.setFitWidth(mapPane.getWidth()); // Adaptez la taille de l'image à la taille du Pane si nécessaire
            mapImageView.setFitHeight(mapPane.getHeight()); // Adaptez la taille de l'image à la taille du Pane si nécessaire
            mapImageView.setPreserveRatio(true);
            mapPane.getChildren().add(mapImageView);
        }
    }
    

    private void drawOffers() {
        ArrayList<EquipmentOffer> allEquipmentOffers = Main.getAllEquipment();
        ArrayList<ServiceOffer> allServiceOffers = Main.getAllService();

        // Compter les offres par ville pour l'équipement
        for (EquipmentOffer offer : allEquipmentOffers) {
            String city = offer.getOwner().getLocalisation();
            offerCountByCity.put(city, offerCountByCity.getOrDefault(city, 0) + 1);
        }

        // Compter les offres par ville pour les services
        for (ServiceOffer offer : allServiceOffers) {
            String city = offer.getSupplier().getLocalisation();
            offerCountByCity.put(city, offerCountByCity.getOrDefault(city, 0) + 1);
        }

        // Dessiner les points pour toutes les offres
        for (String city : offerCountByCity.keySet()) {
            drawOfferPoint(city, offerCountByCity.get(city));
        }
    }
    
    private void drawOfferPoint(String city, int offerCount) {
        double[] gpsCoordinates = getCoordinates(city);
        double[] mapCoordinates = convertGpsToMapCoordinates(gpsCoordinates[0], gpsCoordinates[1], mapImage);
        double circleSize = calculateCircleSize(offerCount);
        Circle circle = new Circle(mapCoordinates[0], mapCoordinates[1], circleSize, Color.RED);
        mapPane.getChildren().add(circle);
    }

    private double calculateCircleSize(int offerCount) {
        // changer taille du cercle en fonction du nombre d'offres
        // par exemple, en utilisant la racine carrée du nombre d'offres
        return 5 + offerCount * 2; 
    }
    
    private double[] convertGpsToMapCoordinates(double latitude, double longitude, Image mapImage) {
        // Bornes de la carte
        double westBound = -5.0;
        double eastBound = 10.0;
        double northBound = 51.0;
        double southBound = 42.0;
    
        // Convertir la longitude en position x relative
        double relativeX = (longitude - westBound) / (eastBound - westBound);
    
        // Convertir la latitude en position y relative (inversée car la latitude diminue vers le sud)
        double relativeY = (northBound - latitude) / (northBound - southBound);
    
        // Convertir en coordonnées de l'image
        double x = relativeX * mapImage.getWidth();
        double y = relativeY * mapImage.getHeight();
    
        return new double[]{x, y};
    }

    private double[] getCoordinates(String cityName) {
        String line;
        InputStream inputStream;
        inputStream = getClass().getResourceAsStream("/eu/telecomnancy/labfx/cities.csv");
        

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = br.readLine()) != null) {
                String[] cityData = line.split(",");
                if (cityData[0].equals("id")) {
                    continue; // Ignorer l'en-tête
                }
                // Vérifier si le nom de la ville correspond (en tenant compte des guillemets)
                String cityInCsv = cityData[4].replace("\"", ""); // Retirer les guillemets
                if (cityInCsv.equalsIgnoreCase(cityName)) {
                    double latitude = Double.parseDouble(cityData[6]);
                    double longitude = Double.parseDouble(cityData[7]);
                    return new double[]{latitude, longitude};
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            e.printStackTrace();
        }
        return new double[]{0.0, 0.0}; // Retourner une valeur par défaut en cas d'échec
    }
}
