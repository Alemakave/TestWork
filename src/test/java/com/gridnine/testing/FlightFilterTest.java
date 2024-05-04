package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightFilterTest {
    @Test
    void filterToValidDate() {
        List<Flight> flights = new ArrayList<>();
        flights.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(1)));
        flights.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3)));
        flights.add(createFlight(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(5)));
        flights.add(createFlight(LocalDateTime.now().plusHours(7), LocalDateTime.now().plusHours(3)));
        flights.add(createFlight(LocalDateTime.now().minusHours(2), LocalDateTime.now().plusHours(4)));
        flights.add(createFlight(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(5)));
        flights.add(createFlight(LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(2)));

        List<Flight> expected = new ArrayList<>();
        expected.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(1)));
        expected.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3)));
        expected.add(createFlight(LocalDateTime.now().plusHours(7), LocalDateTime.now().plusHours(3)));
        expected.add(createFlight(LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(2)));

        List<Flight> actualFlights = new FlightFilter().filterToValidDate(flights);

        assertEquals(expected.size(), actualFlights.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), actualFlights.get(i).toString());
        }
    }

    @Test
    void filterToCorrectArrivalToDepartureDate() {
        List<Flight> flights = new ArrayList<>();
        flights.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(1)));
        flights.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3)));
        flights.add(createFlight(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(5)));
        flights.add(createFlight(LocalDateTime.now().plusHours(7), LocalDateTime.now().plusHours(3)));
        flights.add(createFlight(LocalDateTime.now().minusHours(2), LocalDateTime.now().plusHours(4)));
        flights.add(createFlight(LocalDateTime.now().minusHours(1), LocalDateTime.now().minusHours(5)));
        flights.add(createFlight(LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(2)));

        List<Flight> expected = new ArrayList<>();
        expected.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3)));
        expected.add(createFlight(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(5)));
        expected.add(createFlight(LocalDateTime.now().minusHours(2), LocalDateTime.now().plusHours(4)));

        List<Flight> actualFlights = new FlightFilter().filterToCorrectArrivalToDepartureDate(flights);

        assertEquals(expected.size(), actualFlights.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), actualFlights.get(i).toString());
        }
    }

    @Test
    void filterToTotalTimeSpentOnGroundLessTwoHours() {
        List<Flight> flights = new ArrayList<>();
        flights.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(1),
                                   LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4)));
        flights.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3),
                                   LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5)));
        flights.add(createFlight(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(5),
                                   LocalDateTime.now().plusHours(7), LocalDateTime.now().plusHours(9)));
        flights.add(createFlight(LocalDateTime.now().plusHours(7), LocalDateTime.now().plusHours(3)));
        flights.add(createFlight(LocalDateTime.now().minusHours(2), LocalDateTime.now().plusHours(4),
                                   LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(7)));
        flights.add(createFlight(LocalDateTime.now().minusHours(1), LocalDateTime.now().minusHours(5)));
        flights.add(createFlight(LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(9),
                LocalDateTime.now().plusHours(13), LocalDateTime.now().plusHours(15)));

        List<Flight> expected = new ArrayList<>();
        expected.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(4)));
        expected.add(createFlight(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5)));
        expected.add(createFlight(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusHours(7), LocalDateTime.now().plusHours(9)));
        expected.add(createFlight(LocalDateTime.now().plusHours(7), LocalDateTime.now().plusHours(3)));
        expected.add(createFlight(LocalDateTime.now().minusHours(2), LocalDateTime.now().plusHours(4),
                LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(7)));
        expected.add(createFlight(LocalDateTime.now().minusHours(1), LocalDateTime.now().minusHours(5)));

        List<Flight> actualFlights = new FlightFilter().filterToTotalTimeSpentOnGroundLessTwoHours(flights);

        assertEquals(expected.size(), actualFlights.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), actualFlights.get(i).toString());
        }
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}