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
class CustomerID {

    private final Long customerID;

    public CustomerID(Long customerID) {
        this.customerID = customerID;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.customerID);
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
        final CustomerID other = (CustomerID) obj;
        if (!Objects.equals(this.customerID, other.customerID)) {
            return false;
        }
        return true;
    }
}
