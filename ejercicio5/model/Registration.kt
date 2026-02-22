package com.sistemaeventos.model

import java.time.LocalDateTime

enum class RegistrationStatus { ACTIVE, CANCELLED }

data class Registration(
    val id: String,
    val attendeeId: String,
    val activityId: String,
    val registeredAt: LocalDateTime = LocalDateTime.now(),
    var status: RegistrationStatus = RegistrationStatus.ACTIVE
)