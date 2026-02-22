package com.sistemaeventos.repository.impl

import com.sistemaeventos.model.Attendee
import com.sistemaeventos.repository.AttendeeRepository
import com.sistemaeventos.service.queries.ReadOnlyAttendeeRepository

class InMemoryAttendeeRepository : AttendeeRepository, ReadOnlyAttendeeRepository {
    private val attendees = mutableListOf<Attendee>()

    override fun save(attendee: Attendee) { attendees.add(attendee) }
    override fun findById(id: String): Attendee? = attendees.find { it.id == id }
    override fun findAll(): List<Attendee> = attendees.toList()
    override fun update(attendee: Attendee) {
        val index = attendees.indexOfFirst { it.id == attendee.id }
        if (index != -1) attendees[index] = attendee
    }
    override fun delete(id: String): Boolean = attendees.removeIf { it.id == id }
    override fun findByEmail(email: String): Attendee? = attendees.find { it.email.equals(email, ignoreCase = true) }
}