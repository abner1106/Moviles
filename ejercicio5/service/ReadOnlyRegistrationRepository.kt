package com.sistemaeventos.service.queries

import com.sistemaeventos.model.Registration

interface ReadOnlyRegistrationRepository {
    fun findById(id: String): Registration?
    fun findByAttendee(attendeeId: String): List<Registration>
    fun findByActivity(activityId: String): List<Registration>
    fun findActiveByAttendee(attendeeId: String): List<Registration>
    fun findActiveByActivity(activityId: String): List<Registration>
    fun countActiveByActivity(activityId: String): Int
}