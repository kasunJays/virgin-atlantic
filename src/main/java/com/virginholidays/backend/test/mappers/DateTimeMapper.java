package com.virginholidays.backend.test.validators;


import java.time.LocalDate;
import java.util.Optional;

public interface DateTimeConvertor {

    /**
     *
     * @param date date which needs to be converted.
     * @return LocalDate converted Date if the format is invalid returns an empty optional.
     */
    Optional<LocalDate> convertToLocaleDate(final String date);
}
