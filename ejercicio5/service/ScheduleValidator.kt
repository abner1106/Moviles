package com.sistemaeventos.service.validators

import com.sistemaeventos.exceptions.EventExceptions
import com.sistemaeventos.model.Activity
import com.sistemaeventos.service.queries.ReadOnlyActivityRepository
import com.sistemaeventos.service.queries.ReadOnlyRegistrationRepository
import java.time.LocalTime

class ScheduleValidator(
    private val activityRepo: ReadOnlyActivityRepository,
    private val registrationRepo: ReadOnlyRegistrationRepository
) {
    fun validateNoOverlap(attendeeId: String, newActivity: Activity) {
        val existingRegistrations = registrationRepo.findActiveByAttendee(attendeeId)
        val existingActivities = existingRegistrations.mapNotNull { activityRepo.findById(it.activityId) }

        val overlaps = existingActivities.any { activity ->
            !(newActivity.endTime <= activity.startTime || newActivity.startTime >= activity.endTime)
        }

        if (overlaps) {
            throw EventExceptions.ScheduleOverlapException("El asistente ya tiene una actividad en ese horario")
        }
    }
}