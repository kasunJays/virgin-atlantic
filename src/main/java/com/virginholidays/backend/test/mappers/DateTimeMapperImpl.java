package com.virginholidays.backend.test.validators;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class DateTimeConvertorImpl implements DateTimeConvertor {

    public Optional<LocalDate> convertToLocaleDate(String date) {

        Optional<LocalDate> localDate = null;
        try {
            localDate = Optional.ofNullable(LocalDate.parse(date, DateTimeFormatter.ISO_DATE));
        } catch (DateTimeParseException e) {
            localDate = Optional.empty();
        }

        return localDate;
    }
}
