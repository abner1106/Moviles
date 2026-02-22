package com.sistemaeventos.repository

import com.sistemaeventos.model.Registration
import com.sistemaeventos.model.RegistrationStatus

interface RegistrationRepository {
    fun save(registration: Registration)
    fun findById(id: String): Registration?
    fun findAll(): List<Registration>
    fun findByAttendee(attendeeId: String): List<Registration>
    fun findByActivity(activityId: String): List<Registration>
    fun findActiveByAttendee(attendeeId: String): List<Registration>
    fun findActiveByActivity(activityId: String): List<Registration>
    fun countActiveByActivity(activityId: String): Int
    fun update(registration: Registration)
    fun delete(id: String): Boolean
}