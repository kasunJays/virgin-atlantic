package com.virginholidays.backend.test.service;

import com.virginholidays.backend.test.repository.FlightInfoRepository;
import com.virginholidays.backend.test.validators.DateTimeConvertor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * The FlightInfoServiceImpl unit tests
 *
 * @author Geoff Perks
 */
@ExtendWith(MockitoExtension.class)
class FlightInfoServiceImplTest {

    @InjectMocks
    private FlightInfoServiceImpl service;
    @Mock
    private DateTimeConvertor dateTimeConvertor;

    @Mock
    private FlightInfoRepository flightInfoRepository;

    @Test
    public void testFindByDateWhenDateIsInInvalidFormat() {
        String date = "201404";
        when(dateTimeConvertor.convertToLocaleDate(date)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.findByDate(date));

        String expectedMessage = "Date format is invalid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindByDateWhenDateIsValid() {
        String date = "2014-04-03";
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        when(dateTimeConvertor.convertToLocaleDate(date)).thenReturn(Optional.of(localDate));

        service.findByDate(date);
        Mockito.verify(flightInfoRepository, Mockito.times(1)).findByDate(localDate);
    }
}