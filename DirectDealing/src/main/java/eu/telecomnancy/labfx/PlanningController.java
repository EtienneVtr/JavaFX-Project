package eu.telecomnancy.labfx;

import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;

import java.time.LocalDate;
import java.time.LocalTime;

public class PlanningController {

    @FXML
    private CalendarView planningCalendar;

    private SkeletonController skeleton_controller;
    private User currentUser;
    private Planning planning;
    
    private Calendar serviceOfferedCalendar; // services que j'ai publiés et qui sont réservés
    private Calendar equipmentOfferedCalendar; // équipements que j'ai publiés et qui sont réservés

    public void setSkeletonController(SkeletonController skeleton_controller) {
        this.skeleton_controller = skeleton_controller;
    }

    public void initialize() {
        currentUser = Main.getCurrentUser();
        planning = new Planning(currentUser);

        serviceOfferedCalendar = new Calendar("Services Offered");
        equipmentOfferedCalendar = new Calendar("Equipments Offered");

        // set des styles des calendriers
        serviceOfferedCalendar.setStyle(Calendar.Style.STYLE1);
        equipmentOfferedCalendar.setStyle(Calendar.Style.STYLE2);

        // Ajout des calendriers à la vue
        CalendarSource myCalendarSource = new CalendarSource("Ma Source");
        myCalendarSource.getCalendars().addAll(serviceOfferedCalendar, equipmentOfferedCalendar);
        planningCalendar.getCalendarSources().add(myCalendarSource);

        // on set le calendrier en mois de base
        planningCalendar.showMonthPage();

        // Désactiver le bouton d'ajout de sources de calendrier
        planningCalendar.setShowAddCalendarButton(false);

        // Définir les calendriers en lecture seule
        serviceOfferedCalendar.setReadOnly(true);
        equipmentOfferedCalendar.setReadOnly(true);

        
        AddEventToCalendar();
    }

    public void ajouterEvenement(LocalDate dateDebut, LocalDate dateFin, LocalTime heureDebut, LocalTime heureFin, CombinedOffer offer) {
        Entry<String> evenement = new Entry<>(offer.getTitle());
        evenement.changeStartDate(dateDebut);
        evenement.changeStartTime(heureDebut);
        evenement.changeEndDate(dateFin);
        evenement.changeEndTime(heureFin);
        evenement.setLocation(offer.getDescription());

        if (offer.getType() == CombinedOffer.OfferType.SERVICE_OFFER) {
            serviceOfferedCalendar.addEntry(evenement);
        } else if (offer.getType() == CombinedOffer.OfferType.EQUIPMENT_OFFER) {
            equipmentOfferedCalendar.addEntry(evenement);
        }
    }

    private void AddEventToCalendar() {
        // Vérifiez que les méthodes getMyOffer() et getMyDemand() existent dans la classe Planning
        for (CombinedOffer offer : planning.getMyOffer()) {
            handleOffer(offer);
        }
        for (CombinedOffer offer : planning.getMyDemand()) {
            handleOffer(offer);
        }
    }

    private void handleOffer(CombinedOffer offer) {
        if (offer.getType() == CombinedOffer.OfferType.SERVICE_OFFER) {

            ajouterEvenement(offer.getDate(), offer.getDate(), offer.getTime(), LocalTime.of(23, 59),  offer);
        } else if (offer.getType() == CombinedOffer.OfferType.EQUIPMENT_OFFER) {
;
            ajouterEvenement(offer.getStartAvailability(), offer.getEndAvailability(), LocalTime.of(7, 00), LocalTime.of(23, 59), offer);
        }
    }
}
