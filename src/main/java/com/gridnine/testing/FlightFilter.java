package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilter {
    public List<Flight> filterToValidDate(List<Flight> flights) {
        return flights.stream()
                    .filter(flight -> {
                        boolean correctFlight = true;

                        for (Segment segment : flight.getSegments()) {
                            correctFlight = segment.getDepartureDate().isAfter(LocalDateTime.now());

                            if (!correctFlight) {
                                return false;
                            }
                        }

                        return correctFlight;
                    })
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
                            int count = 0;
                            for (int i = 0; i < segments.size() - 1; i++) {
                                if (i == segments.size() - 1) {
                                    return true;
                                }

                                count += segments.get(i).getArrivalDate().getSecond() - segments.get(i + 1).getDepartureDate().getSecond();

                                if (count > 2 * 60 * 60) {
                                    return false;
                                }
                            }
                        }

                        return true;
                    })
                    .collect(Collectors.toList());
    }
}
