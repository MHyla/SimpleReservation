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
public class ReservationAggregate {

    private final DBOperator dBOperator;

    public ReservationAggregate(DBOperator dBOperator) {
        this.dBOperator = dBOperator;
    }

    public ReservationStatus reserve(ReservationDetails reservationDetails) {
        for (int place : reservationDetails.getPlacesToReserveList().getPlacesList()) {
            if (dBOperator.isPlaceUnavailable(reservationDetails.getEventID(), place)) {
                return ReservationStatus.FAILURE;
            }
        }
        dBOperator.reservePlaces(reservationDetails);

        return ReservationStatus.SUCCESS;
    }
}
