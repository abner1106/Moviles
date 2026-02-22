package com.sistemaeventos.exceptions

sealed class EventException(message: String) : Exception(message)

object EventExceptions {
    class AttendeeNotFoundException(message: String) : EventException(message)
    class ActivityNotFoundException(message: String) : EventException(message)
    class EventNotFoundException(message: String) : EventException(message)
    class ScheduleOverlapException(message: String) : EventException(message)
    class ActivityFullException(message: String) : EventException(message)
}