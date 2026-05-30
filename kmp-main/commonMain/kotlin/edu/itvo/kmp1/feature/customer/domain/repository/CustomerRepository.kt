package edu.itvo.kmp1.feature.customer.domain.repository

import edu.itvo.kmp1.feature.customer.core.repository.BaseRepository
import edu.itvo.kmp1.feature.customer.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository : BaseRepository<Customer, String> {
    override fun observeAll(): Flow<List<Customer>>
    override suspend fun findById(id: String): Customer?
    override suspend fun save(item: Customer, isUpdate: Boolean)
    override suspend fun deleteById(id: String)
}
