package edu.itvo.kmp1.feature.product.data.repository

import edu.itvo.kmp1.feature.product.data.datasource.remote.ProductRemoteDataSource
import edu.itvo.kmp1.feature.product.data.mapper.toDomain
import edu.itvo.kmp1.feature.product.data.mapper.toDto
import edu.itvo.kmp1.feature.product.domain.model.Product
import edu.itvo.kmp1.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class ProductRepositoryImpl(
    private val remote: ProductRemoteDataSource,
    private val local: ProductRepository
) : ProductRepository {

    override fun observeAll(): Flow<List<Product>> = channelFlow {
        val localJob = launch {
            local.observeAll().collect { send(it) }
        }
        refreshProducts()
        localJob.join()
    }

    private suspend fun refreshProducts() {
        try {
            val remoteProducts = remote.getProducts()
            if (remoteProducts.isNotEmpty()) {
                remoteProducts.forEach { dto ->
                    local.save(dto.toDomain(), true)
                }
            }
        } catch (e: Exception) {
            println("NET_DEBUG: Error refreshing products: ${e.message}")
        }
    }

    override suspend fun findById(id: String): Product? {
        return local.findById(id) ?: try {
            remote.getProducts().find { it.id == id || it.code == id }?.toDomain()?.also {
                local.save(it, true)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun save(item: Product, isUpdate: Boolean) {
        local.save(item, isUpdate)
        CoroutineScope(Dispatchers.Default).launch {
            try {
                remote.saveProduct(item.toDto(), isUpdate)
                refreshProducts()
            } catch (e: Exception) {
                println("NET_DEBUG: Error saving product: ${e.message}")
            }
        }
    }

    override suspend fun deleteById(id: String) {
        local.deleteById(id)
        CoroutineScope(Dispatchers.Default).launch {
            try {
                remote.deleteProduct(id)
            } catch (e: Exception) {
                println("NET_DEBUG: Error deleting product: ${e.message}")
            }
        }
    }
}
