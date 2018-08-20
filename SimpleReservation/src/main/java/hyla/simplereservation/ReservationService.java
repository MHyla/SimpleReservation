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
public class ReservationService {

    private final DBOperator dBOperator;

    public ReservationService(DBOperator dBOperator) {
        this.dBOperator = dBOperator;
    }

    public ReservationStatus reserve(ReservationDetails reservationDetails) {

        ReservationStatus raport = new ReservationAggregate(dBOperator).reserve(reservationDetails);

        return raport;
    }
}
