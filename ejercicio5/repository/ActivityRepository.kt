package com.sistemaeventos.repository

import com.sistemaeventos.model.Activity

interface ActivityRepository {
    fun save(activity: Activity)
    fun findById(id: String): Activity?
    fun findAll(): List<Activity>
    fun update(activity: Activity)
    fun delete(id: String): Boolean
    fun findByEvent(eventId: String): List<Activity>
}