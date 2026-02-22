package com.sistemaeventos.service.validators

import com.sistemaeventos.exceptions.EventExceptions
import com.sistemaeventos.model.Activity
import com.sistemaeventos.service.queries.ReadOnlyRegistrationRepository

class CapacityValidator(
    private val registrationRepo: ReadOnlyRegistrationRepository
) {
    fun validateCapacity(activity: Activity) {
        val currentCount = registrationRepo.countActiveByActivity(activity.id)
        if (currentCount >= activity.maxCapacity) {
            throw EventExceptions.ActivityFullException("La actividad '${activity.name}' ha alcanzado su cupo máximo")
        }
    }
}