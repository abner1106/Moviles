package edu.itvo.kmp1.feature.customer.core.repository

import kotlinx.coroutines.flow.Flow

interface BaseRepository<T, ID> {
    fun observeAll(): Flow<List<T>>
    suspend fun findById(id: ID): T?
    suspend fun save(item: T, isUpdate: Boolean = false)
    suspend fun deleteById(id: ID)
}
