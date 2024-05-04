package com.gridnine.testing;

public class Main {
    public static void main(String[] args) {
        for (Flight flight : FlightBuilder.createFlights()) {
            System.out.println(flight);
        }

        FlightFilter flightFilter = new FlightFilter();

        System.out.println("Filter 1");
        System.out.println(flightFilter.filterToValidDate(FlightBuilder.createFlights()));

        System.out.println("Filter 2");
        System.out.println(flightFilter.filterToCorrectArrivalToDepartureDate(FlightBuilder.createFlights()));

        System.out.println("Filter 3");
        System.out.println(flightFilter.filterToTotalTimeSpentOnGroundLessTwoHours(FlightBuilder.createFlights()));
    }
}