/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyla.simplereservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Hylus
 */
class MockDBOperator implements DBOperator {

    @Override
    public boolean isPlaceUnavailable(EventID eventID, int place) {
        Map<Integer, Boolean> placesAvailability = eventPlacesHolder.get(eventID);

        if (placesAvailability.get(place) == false) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void reservePlaces(ReservationDetails reservationDetails) {
        Map<Integer, Boolean> placesAvailability = eventPlacesHolder.get(reservationDetails.getEventID());

        for (int place : reservationDetails.getPlacesToReserveList().getPlacesList()) {
            placesAvailability.put(place, Boolean.FALSE);
        }
        eventPlacesHolder.put(reservationDetails.getEventID(), placesAvailability);

        successfulReservationListHolder.add(reservationDetails);
    }

    private Map<EventID, Map<Integer, Boolean>> eventPlacesHolder = new HashMap<>();
    private Map<Integer, Boolean> emptyEventMap = new HashMap<>();
    private List<ReservationDetails> successfulReservationListHolder = new ArrayList<>();

    public MockDBOperator() {
        fillPlacesMap();
    }

    public void addEvent(EventID eventID) {
        eventPlacesHolder.put(eventID, emptyEventMap);
    }

    public boolean checkReservation(ReservationDetails reservationDetails) {
        for (ReservationDetails reservation : successfulReservationListHolder) {
            if (reservation.getCustomerID().equals(reservationDetails.getCustomerID())) {
                if (reservation.getEventID().equals(reservationDetails.getEventID())) {
                    if (reservation.getPlacesToReserveList().equals(reservationDetails.getPlacesToReserveList())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void fillPlacesMap() {
        for (int placeNumber = 1; placeNumber < 100; placeNumber++) {
            emptyEventMap.put(placeNumber, Boolean.TRUE);
        }
    }

}
