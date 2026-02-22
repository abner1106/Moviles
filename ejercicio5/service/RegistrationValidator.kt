package com.sistemaeventos.service.validators

import com.sistemaeventos.exceptions.EventExceptions
import com.sistemaeventos.service.queries.ReadOnlyActivityRepository
import com.sistemaeventos.service.queries.ReadOnlyAttendeeRepository

class RegistrationValidator(
    private val attendeeRepo: ReadOnlyAttendeeRepository,
    private val activityRepo: ReadOnlyActivityRepository,
    private val scheduleValidator: ScheduleValidator,
    private val capacityValidator: CapacityValidator
) {
    fun validate(attendeeId: String, activityId: String) {
        val attendee = attendeeRepo.findById(attendeeId)
            ?: throw EventExceptions.AttendeeNotFoundException("Asistente no encontrado")

        val activity = activityRepo.findById(activityId)
            ?: throw EventExceptions.ActivityNotFoundException("Actividad no encontrada")

        scheduleValidator.validateNoOverlap(attendee.id, activity)
        capacityValidator.validateCapacity(activity)
    }
}