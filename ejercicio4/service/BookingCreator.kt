package com.sistemreservas.service.processors

import com.sistemreservas.model.Booking
import com.sistemreservas.repository.BookingRepository
import com.sistemreservas.repository.GuestRepository
import com.sistemreservas.repository.RoomRepository
import com.sistemreservas.service.calculators.PriceCalculator
import com.sistemreservas.service.factories.BookingFactory
import com.sistemreservas.service.validators.*
import java.time.LocalDate

class BookingCreator(
    private val roomRepo: RoomRepository,
    private val guestRepo: GuestRepository,
    private val bookingRepo: BookingRepository,
    private val dateValidator: DateValidator,
    private val roomValidator: RoomValidator,
    private val guestValidator: GuestValidator,
    private val availabilityValidator: AvailabilityValidator,
    private val priceCalculator: PriceCalculator,
    private val bookingFactory: BookingFactory
) {
    fun create(guestDni: String, roomNumber: Int, checkIn: LocalDate, checkOut: LocalDate): Booking {
        // 1. Validar fechas
        dateValidator.validateRange(checkIn, checkOut)
        dateValidator.validateNotPast(checkIn)
        val nights = dateValidator.calculateNights(checkIn, checkOut)

        // 2. Obtener y validar huésped
        val guest = guestRepo.findByDni(guestDni)
        guestValidator.validateExists(guest)

        // 3. Obtener y validar habitación
        val room = roomRepo.findByNumber(roomNumber)
        roomValidator.validateExists(room)
        roomValidator.validateAvailable(room!!)

        // 4. Validar disponibilidad
        availabilityValidator.validate(room, checkIn, checkOut)

        // 5. Calcular precio
        val totalPrice = priceCalculator.calculate(room, nights)

        // 6. Crear reserva
        val booking = bookingFactory.create(room, guest!!, checkIn, checkOut, totalPrice)

        // 7. Actualizar estado
        room.available = false
        roomRepo.update(room)
        bookingRepo.save(booking)
        guest.bookingHistory.add(booking)

        return booking
    }
}