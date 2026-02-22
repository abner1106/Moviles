package com.sistemreservas.service.validators

import com.sistemreservas.exceptions.HotelExceptions
import com.sistemreservas.model.Room
import com.sistemreservas.service.queries.ReadOnlyBookingRepository
import java.time.LocalDate

class AvailabilityValidator(
    private val bookingRepo: ReadOnlyBookingRepository   // ISP: solo necesita lectura
) {
    fun validate(room: Room, checkIn: LocalDate, checkOut: LocalDate) {
        val overlapping = bookingRepo.findOverlapping(room.number, checkIn, checkOut)
        if (overlapping.isNotEmpty())
            throw HotelExceptions.RoomNotAvailableException("Habitación ocupada en esas fechas")
    }
}