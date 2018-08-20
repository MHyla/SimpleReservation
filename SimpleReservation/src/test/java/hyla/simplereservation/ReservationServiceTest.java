/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyla.simplereservation;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author Hylus
 */
public class ReservationServiceTest {

    private MockDBOperator mockDBOperator;
    private ReservationService reservationService;

    private HashMap<String, CustomerID> customersMap;
    private HashMap<String, EventID> eventsMap;

    private ReservationStatus status;

    @Before
    public void setUp() {
        mockDBOperator = new MockDBOperator();

        customersMap = new HashMap<>();
        eventsMap = new HashMap<>();
    }

    @org.junit.Test
    public void basicReservation() {
        //given
        customerExist("Marcin");
        eventExist("Stones");

        //when
        customer("Marcin").onEvent("Stones").atPlaces(1).reserve();

        //then
        isSuccess();
        places(1).onEvent("Stones").areReservedBy("Marcin").check();
    }

    @org.junit.Test
    public void failedReservation() {
        //given
        customerExist("Marcin");
        customerExist("Adrian");
        eventExist("Stones");
        customer("Marcin").onEvent("Stones").atPlaces(1).reserve();

        //when
        customer("Adrian").onEvent("Stones").atPlaces(1).reserve();

        //then
        isFailure();
        places(1).onEvent("Stones").areReservedBy("Marcin").check();
    }

    @org.junit.Test
    public void failedReservationv2() {
        //given
        customerExist("Marcin");
        eventExist("Stones");
        customer("Marcin").onEvent("Stones").atPlaces(1).reserve();

        //when
        customer("Marcin").onEvent("Stones").atPlaces(1).reserve();

        //then
        isFailure();
        places(1).onEvent("Stones").areReservedBy("Marcin").check();
    }

    @org.junit.Test
    public void moreThanOnePlace() {
        //given
        customerExist("Marcin");
        eventExist("Stones");

        //when
        customer("Marcin").onEvent("Stones").atPlaces(1, 2, 3).reserve();

        //then
        isSuccess();
        places(1, 2, 3).onEvent("Stones").areReservedBy("Marcin").check();
    }

    @org.junit.Test
    public void moreThanOnePlaceWithOneOfThemBlocked() {
        //given
        customerExist("Marcin");
        customerExist("Adrian");
        eventExist("Stones");
        customer("Adrian").onEvent("Stones").atPlaces(2).reserve();

        //when
        customer("Marcin").onEvent("Stones").atPlaces(1, 2, 3).reserve();

        //then
        isFailure();
        places(2).onEvent("Stones").areReservedBy("Adrian").check();
    }

    @org.junit.Test
    public void basicReservationWhileEventIsNotEmpty() {
        //given
        customerExist("Marcin");
        customerExist("Adrian");
        eventExist("Stones");
        customer("Adrian").onEvent("Stones").atPlaces(2).reserve();

        //when
        customer("Marcin").onEvent("Stones").atPlaces(3, 4).reserve();

        //then
        isSuccess();
        places(2).onEvent("Stones").areReservedBy("Adrian").check();
        places(3, 4).onEvent("Stones").areReservedBy("Marcin").check();
    }

    @org.junit.Test
    public void reservationWhileMoreEventExist() {
        //given
        customerExist("Marcin");
        eventExist("Stones");
        eventExist("Pink");

        //when
        customer("Marcin").onEvent("Pink").atPlaces(1).reserve();

        //then
        isSuccess();
        places(1).onEvent("Pink").areReservedBy("Marcin").check();
    }

    @org.junit.Test
    public void reservationWhileMoreEventsExistAndCustomerIsOnBoth() {
        //given
        customerExist("Marcin");
        eventExist("Stones");
        eventExist("Pink");
        customer("Marcin").onEvent("Pink").atPlaces(1).reserve();

        //when
        customer("Marcin").onEvent("Stones").atPlaces(5).reserve();

        //then
        isSuccess();
        places(1).onEvent("Pink").areReservedBy("Marcin").check();
        places(5).onEvent("Stones").areReservedBy("Marcin").check();
    }

    private void customerExist(String customerName) {
        customersMap.put(customerName, new CustomerID(customersMap.size() + 1L));
    }

    private void eventExist(String eventName) {
        EventID eventID = new EventID(eventsMap.size() + 1L);
        eventsMap.put(eventName, eventID);
        mockDBOperator.addEvent(eventID);
    }

    private ReservationData customer(String customerName) {
        //just for testing purposes where I can control environment (duplicate customerName problem with this setup)
        return new ReservationData(customersMap.get(customerName));
    }

    private AssertReservation places(int... placesArray) {
        //just for testing purposes where I can control environment (duplicate customerName problem with this setup)
        return new AssertReservation(placesArray);
    }

    private void isSuccess() {
        assertTrue(status.equals(status.SUCCESS));
    }

    private void isFailure() {
        assertTrue(status.equals(status.FAILURE));
    }

    public class ReservationData {

        private CustomerID customerID;
        private EventID eventID;
        private PlacesToReserveList placesList;

        public ReservationData(CustomerID customerID) {
            this.customerID = customerID;
        }

        private ReservationData onEvent(String eventName) {
            //just for testing purposes where I can control environment (duplicate eventName problem with this setup)
            eventID = eventsMap.get(eventName);
            return this;
        }

        private ReservationData atPlaces(int... placesArray) {
            ArrayList<Integer> tempPlacesList = new ArrayList<>();
            for (int place : placesArray) {
                tempPlacesList.add(place);
            }
            placesList = new PlacesToReserveList(tempPlacesList);
            return this;
        }

        private void reserve() {
            reservationService = new ReservationService(mockDBOperator);
            status = reservationService.reserve(new ReservationDetails(customerID, eventID, placesList));
        }
    }

    public class AssertReservation {

        private PlacesToReserveList placesList;
        private EventID eventID;
        private CustomerID customerID;

        public AssertReservation(int... placesArray) {
            ArrayList<Integer> tempPlacesList = new ArrayList<>();
            for (int place : placesArray) {
                tempPlacesList.add(place);
            }
            placesList = new PlacesToReserveList(tempPlacesList);
        }

        private AssertReservation onEvent(String eventName) {
            eventID = eventsMap.get(eventName);
            return this;
        }

        private AssertReservation areReservedBy(String customerName) {
            this.customerID = customersMap.get(customerName);
            return this;
        }

        private void check() {
            assertTrue(mockDBOperator.checkReservation(new ReservationDetails(customerID, eventID, placesList)));
        }
    }
}
