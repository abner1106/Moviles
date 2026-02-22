package com.sistemaeventos.service.queries

import com.sistemaeventos.model.Attendee

interface ReadOnlyAttendeeRepository {
    fun findById(id: String): Attendee?
    fun findByEmail(email: String): Attendee?
}