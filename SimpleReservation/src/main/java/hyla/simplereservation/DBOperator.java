/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyla.simplereservation;

/**
 *
 * @author Hylus
 */
interface DBOperator {

    public boolean isPlaceUnavailable(EventID eventID, int place);

    public void reservePlaces(ReservationDetails reservationDetails);
    
}
