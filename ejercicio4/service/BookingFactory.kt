package com.sistemreservas.service.factories

import com.sistemreservas.model.Booking
import com.sistemreservas.model.Guest
import com.sistemreservas.model.Room
import java.time.LocalDate
import java.util.UUID

class BookingFactory {
    fun create(
        room: Room,
        guest: Guest,
        checkIn: LocalDate,
        checkOut: LocalDate,
        totalPrice: Double
    ): Booking = Booking(
        id = UUID.randomUUID().toString(),
        room = room,
        guest = guest,
        checkIn = checkIn,
        checkOut = checkOut,
        totalPrice = totalPrice
    )
}