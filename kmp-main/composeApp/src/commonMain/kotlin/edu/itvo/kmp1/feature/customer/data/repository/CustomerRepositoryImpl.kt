package edu.itvo.kmp1.feature.customer.data.repository

import edu.itvo.kmp1.feature.customer.data.datasource.remote.CustomerRemoteDataSource
import edu.itvo.kmp1.feature.customer.data.mapper.toDomain
import edu.itvo.kmp1.feature.customer.data.mapper.toDto
import edu.itvo.kmp1.feature.customer.domain.model.Customer
import edu.itvo.kmp1.feature.customer.domain.repository.CustomerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CustomerRepositoryImpl(
    private val remote: CustomerRemoteDataSource,
    private val local: CustomerRepository
) : CustomerRepository {

    override fun observeAll(): Flow<List<Customer>> = channelFlow {
        val localJob = launch {
            local.observeAll().collect { send(it) }
        }
        refreshCustomers()
        localJob.join()
    }

    private suspend fun refreshCustomers() {
        try {
            val remoteCustomers = remote.getCustomers()
            if (remoteCustomers.isNotEmpty()) {
                remoteCustomers.forEach { dto ->
                    local.save(dto.toDomain(), true)
                }
            }
        } catch (e: Exception) {
            println("NET_DEBUG: Error refreshing customers: ${e.message}")
        }
    }

    override suspend fun findById(id: String): Customer? {
        return local.findById(id) ?: try {
             remote.getCustomers().find { it.id == id || it.code == id }?.toDomain()?.also {
                 local.save(it, true)
             }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun save(item: Customer, isUpdate: Boolean) {
        // Guardar localmente para respuesta inmediata
        local.save(item, isUpdate)
        
        // Guardar en el servidor
        CoroutineScope(Dispatchers.Default).launch {
            try {
                remote.saveCustomer(item.toDto(), isUpdate)
                refreshCustomers()
            } catch (e: Exception) {
                println("NET_DEBUG: Error saving customer: ${e.message}")
            }
        }
    }

    override suspend fun deleteById(id: String) {
        local.deleteById(id)
        CoroutineScope(Dispatchers.Default).launch {
            try {
                remote.deleteCustomer(id)
            } catch (e: Exception) {
                println("NET_DEBUG: Error deleting customer: ${e.message}")
            }
        }
    }
}
