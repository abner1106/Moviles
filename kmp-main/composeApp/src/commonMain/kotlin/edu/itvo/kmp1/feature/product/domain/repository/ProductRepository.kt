package edu.itvo.kmp1.feature.product.domain.repository

import edu.itvo.kmp1.feature.product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeAll(): Flow<List<Product>>
    suspend fun findById(id: String): Product?
    suspend fun save(item: Product, isUpdate: Boolean) // Añadido isUpdate
    suspend fun deleteById(id: String)
}
