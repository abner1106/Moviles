package com.sistemaeventos.repository.impl

import com.sistemaeventos.model.Registration
import com.sistemaeventos.model.RegistrationStatus
import com.sistemaeventos.repository.RegistrationRepository
import com.sistemaeventos.service.queries.ReadOnlyRegistrationRepository

class InMemoryRegistrationRepository : RegistrationRepository, ReadOnlyRegistrationRepository {
    private val registrations = mutableListOf<Registration>()

    override fun save(registration: Registration) { registrations.add(registration) }
    override fun findById(id: String): Registration? = registrations.find { it.id == id }
    override fun findAll(): List<Registration> = registrations.toList()
    override fun findByAttendee(attendeeId: String): List<Registration> = registrations.filter { it.attendeeId == attendeeId }
    override fun findByActivity(activityId: String): List<Registration> = registrations.filter { it.activityId == activityId }
    override fun findActiveByAttendee(attendeeId: String): List<Registration> = registrations.filter { it.attendeeId == attendeeId && it.status == RegistrationStatus.ACTIVE }
    override fun findActiveByActivity(activityId: String): List<Registration> = registrations.filter { it.activityId == activityId && it.status == RegistrationStatus.ACTIVE }
    override fun countActiveByActivity(activityId: String): Int = registrations.count { it.activityId == activityId && it.status == RegistrationStatus.ACTIVE }
    override fun update(registration: Registration) {
        val index = registrations.indexOfFirst { it.id == registration.id }
        if (index != -1) registrations[index] = registration
    }
    override fun delete(id: String): Boolean = registrations.removeIf { it.id == id }
}