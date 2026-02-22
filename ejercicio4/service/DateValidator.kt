package com.sistemreservas.service.validators

import com.sistemreservas.exceptions.HotelExceptions
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DateValidator {
    fun validateRange(checkIn: LocalDate, checkOut: LocalDate) {
        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut))
            throw HotelExceptions.InvalidDateRangeException("Check-out debe ser posterior a check-in")
    }

    fun validateNotPast(checkIn: LocalDate) {
        if (checkIn.isBefore(LocalDate.now()))
            throw HotelExceptions.InvalidDateRangeException("No se permiten fechas pasadas")
    }

    fun calculateNights(checkIn: LocalDate, checkOut: LocalDate): Long =
        ChronoUnit.DAYS.between(checkIn, checkOut)
}