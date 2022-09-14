package com.virginholidays.backend.test.repository;

import com.virginholidays.backend.test.api.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * The FlightInfoRepository
 *
 * @author Geoff Perks
 */
public interface FlightInfoRepository {

    /**
     * Finds all the flights available
     *
     * @return an optional of all the flights
     */
    CompletionStage<Optional<List<Flight>>> findAll();

    /**
     * Finds the flights available for the given date
     * @param date outBoundDate
     * @return an optional of all the flights
     */
    CompletionStage<Optional<List<Flight>>> findByDate(LocalDate date);
}
