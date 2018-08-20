/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyla.simplereservation;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Hylus
 */
class PlacesToReserveList {

    private final ArrayList<Integer> placesList;

    public ArrayList<Integer> getPlacesList() {
        return placesList;
    }

    public PlacesToReserveList(ArrayList placesList) {
        this.placesList = placesList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.placesList);
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
        final PlacesToReserveList other = (PlacesToReserveList) obj;
        if (!Objects.equals(this.placesList, other.placesList)) {
            return false;
        }
        return true;
    }
}
