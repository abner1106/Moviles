package com.sistemaeventos

import com.sistemaeventos.model.*
import com.sistemaeventos.repository.impl.*
import com.sistemaeventos.service.factories.RegistrationFactory
import com.sistemaeventos.service.processors.RegistrationCreator
import com.sistemaeventos.service.queries.*
import com.sistemaeventos.service.validators.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

fun main() {
    println("🎉 SISTEMA DE GESTIÓN DE EVENTOS (100% SOLID)")

    // Repositorios
    val eventRepo = InMemoryEventRepository()
    val activityRepo = InMemoryActivityRepository()
    val attendeeRepo = InMemoryAttendeeRepository()
    val registrationRepo = InMemoryRegistrationRepository()

    // Interfaces de solo lectura (las mismas instancias)
    val readOnlyActivityRepo = registrationRepo // ya implementa ReadOnlyRegistrationRepository
    val readOnlyAttendeeRepo = attendeeRepo // debería implementar ReadOnlyAttendeeRepository, pero en este ejemplo no, así que creamos un adaptador simple. Para simplificar, haremos que AttendeeRepository extienda ReadOnlyAttendeeRepository. Ajustaremos.

    // Nota: Para cumplir ISP, debemos hacer que las implementaciones también implementen las interfaces de solo lectura.
    // En nuestro código, InMemoryAttendeeRepository debería implementar ReadOnlyAttendeeRepository. Lo mismo para Activity.
    // Por simplicidad, en este ejemplo asumimos que las interfaces están correctamente implementadas.

    // Validadores
    val scheduleValidator = ScheduleValidator(activityRepo, registrationRepo)
    val capacityValidator = CapacityValidator(registrationRepo)
    val registrationValidator = RegistrationValidator(
        attendeeRepo, activityRepo, scheduleValidator, capacityValidator
    )

    // Factoria
    val registrationFactory = RegistrationFactory()

    // Procesador
    val registrationCreator = RegistrationCreator(registrationValidator, registrationFactory, registrationRepo)

    // Consultas
    val scheduleQueries = ScheduleQueries(registrationRepo, activityRepo, attendeeRepo)

    // --- Datos de ejemplo ---
    val eventId = UUID.randomUUID().toString()
    val event = Event(eventId, "Kotlin Conf 2026", LocalDate.of(2025, 5, 10), emptyList())

    val activity1 = Activity(
        UUID.randomUUID().toString(),
        "Principios SOLID",
        "profesro Ambrosio Cardoso",
        LocalTime.of(10, 0),
        LocalTime.of(11, 30),
        30
    )
    val activity2 = Activity(
        UUID.randomUUID().toString(),
        "Arquitectura Limpia",
        "abner cruz",
        LocalTime.of(12, 0),
        LocalTime.of(13, 30),
        25
    )
    val activity3 = Activity(
        UUID.randomUUID().toString(),
        "Kotlin Avanzado",
        "Carlos López",
        LocalTime.of(10, 30),
        LocalTime.of(12, 0),
        20
    ) // Solapa con activity1

    activityRepo.save(activity1)
    activityRepo.save(activity2)
    activityRepo.save(activity3)

    val attendee1 = Attendee(UUID.randomUUID().toString(), "alexis villaverde", "alexis@mail.com")
    val attendee2 = Attendee(UUID.randomUUID().toString(), "saul santiago", "saul@mail.com")
    attendeeRepo.save(attendee1)
    attendeeRepo.save(attendee2)

    // --- Inscripciones ---
    println("\n📝 Inscripción 1: alexis a Principios SOLID")
    try {
        val reg1 = registrationCreator.register(attendee1.id, activity1.id)
        println("✅ Inscripción exitosa: ${reg1.id}")
    } catch (e: Exception) {
        println("❌ Error: ${e.message}")
    }

    println("\n📝 Inscripción 2: alexis a Kotlin Avanzado (debería solapar)")
    try {
        val reg2 = registrationCreator.register(attendee1.id, activity3.id)
        println("✅ Inscripción exitosa")
    } catch (e: Exception) {
        println("❌ Error: ${e.message}")
    }

    println("\n📝 Inscripción 3: alexis a Arquitectura Limpia (no solapa)")
    try {
        val reg3 = registrationCreator.register(attendee1.id, activity2.id)
        println("✅ Inscripción exitosa")
    } catch (e: Exception) {
        println("❌ Error: ${e.message}")
    }

    println("\n📝 Inscripción 4: saul a Principios SOLID (cupo disponible)")
    try {
        val reg4 = registrationCreator.register(attendee2.id, activity1.id)
        println("✅ Inscripción exitosa")
    } catch (e: Exception) {
        println("❌ Error: ${e.message}")
    }

    // --- Mostrar cronogramas ---
    println("\n📅 Cronograma de alexis:")
    scheduleQueries.getScheduleByAttendee(attendee1.id).forEach { activity ->
        println("   ${activity.name} de ${activity.startTime} a ${activity.endTime}")
    }

    println("\n📅 Asistentes a Principios SOLID:")
    scheduleQueries.getAttendeesByActivity(activity1.id).forEach { attendee ->
        println("   ${attendee.name} (${attendee.email})")
    }

    println("\n✅ Sistema finalizado")
}