package com.sistemreservas

import com.sistemreservas.model.*
import com.sistemreservas.repository.impl.*
import com.sistemreservas.service.calculators.PriceCalculator
import com.sistemreservas.service.factories.BookingFactory
import com.sistemreservas.service.processors.BookingCreator
import com.sistemreservas.service.processors.BookingCanceller
import com.sistemreservas.service.queries.BookingQueries
import com.sistemreservas.service.validators.*
import java.time.LocalDate

fun main() {
    println("🏨 SISTEMA DE RESERVAS HOTEL CHIVIRITVO")

    // Repositorios
    val roomRepo = InMemoryRoomRepository()
    val guestRepo = InMemoryGuestRepository()
    val bookingRepo = InMemoryBookingRepository()   // implementa ambas interfaces

    // Validadores
    val dateValidator = DateValidator()
    val roomValidator = RoomValidator()
    val guestValidator = GuestValidator()
    val availabilityValidator = AvailabilityValidator(bookingRepo)  // usa ReadOnlyBookingRepository

    // Calculadora
    val priceCalculator = PriceCalculator()

    // Fábrica
    val bookingFactory = BookingFactory()

    // Procesadores
    val bookingCreator = BookingCreator(
        roomRepo, guestRepo, bookingRepo,
        dateValidator, roomValidator, guestValidator,
        availabilityValidator, priceCalculator, bookingFactory
    )
    val bookingCanceller = BookingCanceller(bookingRepo, roomRepo)

    // Consultas
    val bookingQueries = BookingQueries(bookingRepo, guestRepo)

    // --- Datos de ejemplo ---
    val habitacion1 = Room(101, RoomType.STANDARD, 100.0)
    val habitacion2 = Room(201, RoomType.DELUXE, 150.0)
    roomRepo.save(habitacion1)
    roomRepo.save(habitacion2)

    val guest = Guest("12345678A", "Juan Pérez", "juan@mail.com")
    guestRepo.save(guest)

    // Crear reserva
    val reserva = bookingCreator.create("12345678A", 101, LocalDate.now().plusDays(5), LocalDate.now().plusDays(8))
    println("✅ Reserva creada: ${reserva.id} - Total: $${reserva.totalPrice}")

    // Consultar reservas activas
    println("📋 Reservas activas: ${bookingQueries.findActive().size}")

    // Cancelar reserva
    bookingCanceller.cancel(reserva.id)
    println("❌ Reserva cancelada")

    // Ver historial
    val stats = bookingQueries.getGuestStats("12345678A")
    println("📊 Historial: ${stats}")
}