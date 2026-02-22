package com.sistemaeventos.repository.impl

import com.sistemaeventos.model.Activity
import com.sistemaeventos.repository.ActivityRepository
import com.sistemaeventos.service.queries.ReadOnlyActivityRepository

class InMemoryActivityRepository : ActivityRepository, ReadOnlyActivityRepository {
    private val activities = mutableListOf<Activity>()

    override fun save(activity: Activity) { activities.add(activity) }
    override fun findById(id: String): Activity? = activities.find { it.id == id }
    override fun findAll(): List<Activity> = activities.toList()
    override fun update(activity: Activity) {
        val index = activities.indexOfFirst { it.id == activity.id }
        if (index != -1) activities[index] = activity
    }
    override fun delete(id: String): Boolean = activities.removeIf { it.id == id }
    override fun findByEvent(eventId: String): List<Activity> = activities.filter { it.id.startsWith(eventId) }
}