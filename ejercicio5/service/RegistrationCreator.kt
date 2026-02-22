package com.sistemaeventos.service.processors

import com.sistemaeventos.model.Registration
import com.sistemaeventos.repository.RegistrationRepository
import com.sistemaeventos.service.factories.RegistrationFactory
import com.sistemaeventos.service.validators.RegistrationValidator

class RegistrationCreator(
    private val registrationValidator: RegistrationValidator,
    private val registrationFactory: RegistrationFactory,
    private val registrationRepo: RegistrationRepository
) {
    fun register(attendeeId: String, activityId: String): Registration {
        // 1. Validar
        registrationValidator.validate(attendeeId, activityId)

        // 2. Crear
        val registration = registrationFactory.create(attendeeId, activityId)

        // 3. Guardar
        registrationRepo.save(registration)

        return registration
    }
}