package com.virginholidays.backend.test.resource;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.service.FlightInfoService;
import com.virginholidays.backend.test.validators.DateTimeConvertor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;


import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


/**
 * The FlightInfoResource unit tests
 *
 * @author Geoff Perks
 */
@ExtendWith(MockitoExtension.class)
class FlightInfoResourceTest {
    @InjectMocks
    private FlightInfoResource flightInfoResource;

    @Mock
    private FlightInfoService flightInfoService;

    @Mock
    private DateTimeConvertor dateTimeConvertor;

    @Test
    public void testFindByDateWhenDataExists() throws ExecutionException, InterruptedException {

        String date="2014-03-04";
        final Flight flight = new Flight(LocalTime.NOON,"dest","IATA","AC00", Arrays.asList(DayOfWeek.SUNDAY));
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        CompletionStage<Optional<List<Flight>>> flights = completedFuture(Optional.ofNullable(Arrays.asList(flight)));

        when(flightInfoService.findByDate(date)).thenReturn(flights);
        when(dateTimeConvertor.convertToLocaleDate(date)).thenReturn(Optional.of(localDate));

        CompletableFuture<ResponseEntity<?>> result= (CompletableFuture<ResponseEntity<?>>) flightInfoResource.getResults(date);

        assertThat(result.get().getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void testFindByDateWhenDataNotExists() throws ExecutionException, InterruptedException {

        String date="2014-03-04";
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        when(flightInfoService.findByDate(date)).thenReturn(completedFuture(Optional.ofNullable(Collections.emptyList())));
        when(dateTimeConvertor.convertToLocaleDate(date)).thenReturn(Optional.of(localDate));

        CompletableFuture<ResponseEntity<?>> result= (CompletableFuture<ResponseEntity<?>>) flightInfoResource.getResults(date);

        assertThat(result.get().getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    public void testFindByDateWhenDateIsInInvalidFormat() throws ExecutionException, InterruptedException {

        String date="201404";
        when(dateTimeConvertor.convertToLocaleDate(date)).thenReturn(Optional.empty());

        CompletableFuture<ResponseEntity<?>> result= (CompletableFuture<ResponseEntity<?>>) flightInfoResource.getResults(date);

        assertThat(result.get().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

}
