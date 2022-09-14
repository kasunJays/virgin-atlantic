package com.virginholidays.backend.test.resource;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.service.FlightInfoService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.validation.constraints.NotEmpty;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.springframework.http.CacheControl.noCache;

import com.virginholidays.backend.test.validators.DateTimeConvertor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.status;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Geoff Perks
 *
 * The FlightInfoResource
 */
@RestController
public class FlightInfoResource {

    private final FlightInfoService flightInfoService;
    private final DateTimeConvertor dateTimeConvertor;



    /**
     * The constructor
     *
     * @param flightInfoService the flightInfoService
     * @param dateTimeConvertor the dateTimeConvertor
     */
    public FlightInfoResource(final FlightInfoService flightInfoService, final DateTimeConvertor dateTimeConvertor) {
        this.flightInfoService = flightInfoService;
        this.dateTimeConvertor = dateTimeConvertor;
    }

    /**
     * Resource method for returning flight results
     *
     * @param date the chosen date
     * @return flights for the day of the chosen date
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{date}/results")
    public CompletionStage<ResponseEntity<?>> getResults(@PathVariable("date") @NotEmpty String date) {

        Optional<LocalDate> localDate = dateTimeConvertor.convertToLocaleDate(date);
        if (localDate.isPresent()) {
            return flightInfoService.findByDate(date).thenApply(maybeResults -> {

                List<Flight> results = maybeResults.get();

                // no results, no content
                if (results.isEmpty()) {
                    return status(HttpStatus.NO_CONTENT).cacheControl(noCache()).build();
                }

                return status(HttpStatus.OK).cacheControl(noCache()).body(results);
            });
        }else {
            return completedFuture(status(HttpStatus.BAD_REQUEST).cacheControl(noCache()).build());
        }

    }
}
