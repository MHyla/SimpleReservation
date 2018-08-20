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
class EventID {

    private final Long eventID;

    public EventID(Long eventID) {
        this.eventID = eventID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.eventID);
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
        final EventID other = (EventID) obj;
        if (!Objects.equals(this.eventID, other.eventID)) {
            return false;
        }
        return true;
    }
}
