package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilter {
    public List<Flight> filterToValidDate(List<Flight> flights) {
        return flights.stream()
                    .filter(flight -> flight.getSegments().get(0).getDepartureDate().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
    }

    public List<Flight> filterToCorrectArrivalToDepartureDate(List<Flight> flights) {
        return flights.stream()
                    .filter(flight -> {
                        for (Segment segment : flight.getSegments()) {
                            if (segment.getDepartureDate().isAfter(segment.getArrivalDate())) {
                                return false;
                            }
                        }

                        return true;
                    })
                    .collect(Collectors.toList());
    }

    public List<Flight> filterToTotalTimeSpentOnGroundLessTwoHours(List<Flight> flights) {
        return flights.stream()
                    .filter(flight -> {
                        List<Segment> segments = flight.getSegments();
                        if (segments.size() > 1) {
                            for (int i = 0; i < segments.size() - 1; i++) {
                                if (i == segments.size() - 1) {
                                    return true;
                                }

                                if (segments.get(i).getArrivalDate().plusHours(2).isBefore(segments.get(i + 1).getDepartureDate())) {
                                    return false;
                                }
                            }
                        }

                        return true;
                    })
                    .collect(Collectors.toList());
    }
}
