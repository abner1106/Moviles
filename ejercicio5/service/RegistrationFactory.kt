package com.sistemaeventos.service.factories

import com.sistemaeventos.model.Registration
import java.util.UUID

class RegistrationFactory {
    fun create(attendeeId: String, activityId: String): Registration {
        return Registration(
            id = UUID.randomUUID().toString(),
            attendeeId = attendeeId,
            activityId = activityId
        )
    }
}