package eu.telecomnancy.labfx;

import javafx.scene.input.MouseEvent;

import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;


import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;

public class PlanningController {

    @FXML
    private CalendarView planningCalendar;

    private SkeletonController skeleton_controller;
    private User currentUser;
    private Planning planning;
    
    private Calendar serviceOfferedCalendar; // services que j'ai publiés et qui sont réservés
    private Calendar equipmentOfferedCalendar; // équipements que j'ai publiés et qui sont réservés
    private Calendar serviceNoneOfferedCalendar; // services que j'ai publiés et qui ne sont pas réservés
    private Calendar equipmentNoneOfferedCalendar; // équipements que j'ai publiés et qui ne sont pas réservés
    private Calendar serviceDemandedCalendar; // services que j'ai réservés
    private Calendar equipmentDemandedCalendar; // équipements que j'ai réservés

    public void setSkeletonController(SkeletonController skeleton_controller) {
        this.skeleton_controller = skeleton_controller;
    }

    public void initialize() {
        currentUser = Main.getCurrentUser();
        planning = new Planning(currentUser);
        planningCalendar.setEntryFactory(param -> null);



        serviceOfferedCalendar = new Calendar("Services Proposés accepté");
        equipmentOfferedCalendar = new Calendar("Equipments Proposés accepté");
        serviceNoneOfferedCalendar = new Calendar("Services proposé non accepté");
        equipmentNoneOfferedCalendar = new Calendar("Equipments proposé non accepté");
        serviceDemandedCalendar = new Calendar("Services demandé");
        equipmentDemandedCalendar = new Calendar("Equipments demandé");

        // set des styles des calendriers
        serviceOfferedCalendar.setStyle(Calendar.Style.STYLE1);
        equipmentOfferedCalendar.setStyle(Calendar.Style.STYLE2);
        serviceNoneOfferedCalendar.setStyle(Calendar.Style.STYLE3);
        equipmentNoneOfferedCalendar.setStyle(Calendar.Style.STYLE4);
        serviceDemandedCalendar.setStyle(Calendar.Style.STYLE5);
        equipmentDemandedCalendar.setStyle(Calendar.Style.STYLE6);

        // Ajout des calendriers à la vue
        CalendarSource myCalendarSource = new CalendarSource("Ma Source");
        myCalendarSource.getCalendars().addAll(serviceOfferedCalendar, equipmentOfferedCalendar, serviceNoneOfferedCalendar, equipmentNoneOfferedCalendar, serviceDemandedCalendar, equipmentDemandedCalendar);
        planningCalendar.getCalendarSources().add(myCalendarSource);

        // on set le calendrier en mois de base
        planningCalendar.showMonthPage();

        // Désactiver le bouton d'ajout de sources de calendrier
        planningCalendar.setShowAddCalendarButton(false);

        // Définir les calendriers en lecture seule
        serviceOfferedCalendar.setReadOnly(true);
        equipmentOfferedCalendar.setReadOnly(true);
        serviceNoneOfferedCalendar.setReadOnly(true);
        equipmentNoneOfferedCalendar.setReadOnly(true);
        serviceDemandedCalendar.setReadOnly(true);
        equipmentDemandedCalendar.setReadOnly(true);

        
        AddEventToCalendar();
    }

    public void ajouterEvenement(CombinedOffer offer) {
        System.out.println("ajouterEvenement");
        Entry<String> evenement = new Entry<String>(offer.getTitle());
        
        // Définir des valeurs par défaut si nécessaire
        dateDebut = (offer.getStart() == null) ? LocalDate.now() : dateDebut;
        dateFin = (offer.getEnd() == null) ? dateDebut : dateFin;
        heureDebut = (offer == null) ? LocalTime.of(0, 0) : heureDebut;
        heureFin = (heureFin == null) ? LocalTime.of(23, 59) : heureFin;

        // Configurer l'événement
        evenement.changeStartDate(dateDebut);
        evenement.changeStartTime(heureDebut);
        evenement.changeEndDate(dateFin);
        evenement.changeEndTime(heureFin);

        // Sélectionner le calendrier approprié
        Calendar calendar;
        if (offer.getType() == CombinedOffer.OfferType.SERVICE_OFFER) {
            calendar = (offer.getEstPris() == null) ? serviceNoneOfferedCalendar :
                    (offer.getOwner().getMail().equals(currentUser.getMail())) ? serviceOfferedCalendar :
                    serviceDemandedCalendar;
        } else {
            calendar = (offer.getEstPris() == null) ? equipmentNoneOfferedCalendar :
                    (offer.getOwner().getMail().equals(currentUser.getMail())) ? equipmentOfferedCalendar :
                    equipmentDemandedCalendar;
        }

        // Ajouter l'entrée au calendrier sélectionné
        calendar.addEntry(evenement);
    }

    private void AddEventToCalendar() {
        // update permet de mettre tout les event dans lesquels participent user
        planning.update();
        // Vérifiez que les méthodes getMyOffer() et getMyDemand() existent dans la classe Planning
        System.out.println("planning.getMyOffer()");
        for (CombinedOffer offer : planning.getMyOffer()) {
            handleOffer(offer);
        }
        for (CombinedOffer offer : planning.getMyDemand()) {
            handleOffer(offer);
        }
    }

    private void handleOffer(CombinedOffer offer) {
        System.out.println("offer.getType()" );
        if (offer.getType() == CombinedOffer.OfferType.SERVICE_OFFER) {

            ajouterEvenement(offer);
            ajouterEvenement(offer);
        } else if (offer.getType() == CombinedOffer.OfferType.EQUIPMENT_OFFER) {
            ajouterEvenement(offer);
        }
    }
}
