/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyla.simplereservation;

import java.util.Objects;

/**
 *
 * @author Hylus
 */
class ReservationDetails {

    private final CustomerID customerID;
    private final EventID eventID;
    private final PlacesToReserveList placesToReserveList;

    public ReservationDetails(CustomerID customerID, EventID eventID, PlacesToReserveList placesToReserveList) {
        this.customerID = customerID;
        this.eventID = eventID;
        this.placesToReserveList = placesToReserveList;
    }

    public CustomerID getCustomerID() {
        return customerID;
    }

    public EventID getEventID() {
        return eventID;
    }

    public PlacesToReserveList getPlacesToReserveList() {
        return placesToReserveList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.customerID);
        hash = 83 * hash + Objects.hashCode(this.eventID);
        hash = 83 * hash + Objects.hashCode(this.placesToReserveList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReservationDetails other = (ReservationDetails) obj;
        if (!Objects.equals(this.customerID, other.customerID)) {
            return false;
        }
        if (!Objects.equals(this.eventID, other.eventID)) {
            return false;
        }
        if (!Objects.equals(this.placesToReserveList, other.placesToReserveList)) {
            return false;
        }
        return true;
    }
}
