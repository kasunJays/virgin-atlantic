package com.virginholidays.backend.test.service;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.repository.FlightInfoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import com.virginholidays.backend.test.validators.DateTimeConvertor;
import org.springframework.stereotype.Service;

/**
 * The service implementation of FlightInfoService
 *
 * @author Geoff Perks
 */
@Service
public class FlightInfoServiceImpl implements FlightInfoService {

    private final FlightInfoRepository flightInfoRepository;

    private final DateTimeConvertor dateTimeConvertor;

    /**
     * The constructor
     *
     * @param flightInfoRepository the flightInfoRepository
     * @param dateTimeConvertor the dateTimeConvertor
     */
    public FlightInfoServiceImpl(FlightInfoRepository flightInfoRepository, DateTimeConvertor dateTimeConvertor) {
        this.flightInfoRepository = flightInfoRepository;
        this.dateTimeConvertor = dateTimeConvertor;
    }

    public CompletionStage<Optional<List<Flight>>> findByDate(final String date) {

        Optional<LocalDate> localDate = dateTimeConvertor.convertToLocaleDate(date);
        if(localDate.isPresent()) {
            return flightInfoRepository.findByDate(localDate.get());
        }
        throw new IllegalArgumentException("Date format is invalid. Date should be in yyyy-mm-dd format");
    }
}
