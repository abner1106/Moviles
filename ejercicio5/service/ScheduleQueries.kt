package com.sistemaeventos.service.queries

import com.sistemaeventos.model.Activity
import com.sistemaeventos.model.Attendee
import com.sistemaeventos.model.Registration

class ScheduleQueries(
    private val registrationRepo: ReadOnlyRegistrationRepository,
    private val activityRepo: ReadOnlyActivityRepository,
    private val attendeeRepo: ReadOnlyAttendeeRepository
) {
    fun getScheduleByAttendee(attendeeId: String): List<Activity> {
        return registrationRepo.findActiveByAttendee(attendeeId)
            .mapNotNull { activityRepo.findById(it.activityId) }
            .sortedBy { it.startTime }
    }

    fun getScheduleByActivity(activityId: String): List<Attendee> {
        return registrationRepo.findActiveByActivity(activityId)
            .mapNotNull { attendeeRepo.findById(it.attendeeId) }
    }

    fun getAttendeesByActivity(activityId: String): List<Attendee> = getScheduleByActivity(activityId)
    fun getActivitiesByAttendee(attendeeId: String): List<Activity> = getScheduleByAttendee(attendeeId)
}