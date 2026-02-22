package com.sistemreservas.service.queries

import com.sistemreservas.model.Booking
import java.time.LocalDate

interface ReadOnlyBookingRepository {
    fun findById(id: String): Booking?
    fun findByGuest(dni: String): List<Booking>
    fun findByRoom(roomNumber: Int): List<Booking>
    fun findActive(): List<Booking>
    fun findOverlapping(roomNumber: Int, checkIn: LocalDate, checkOut: LocalDate): List<Booking>
    fun findAll(): List<Booking>
}