package com.sistemreservas.repository

import com.sistemreservas.model.Booking
import com.sistemreservas.model.BookingStatus
import java.time.LocalDate

interface BookingRepository {
    fun save(booking: Booking)
    fun findById(id: String): Booking?
    fun findAll(): List<Booking>
    fun findByGuest(dni: String): List<Booking>
    fun findByRoom(roomNumber: Int): List<Booking>
    fun findActive(): List<Booking>
    fun findOverlapping(roomNumber: Int, checkIn: LocalDate, checkOut: LocalDate): List<Booking>
    fun update(booking: Booking)
    fun delete(id: String): Boolean
}