package edu.itvo.kmp1.feature.product.domain.usecase

import edu.itvo.kmp1.feature.product.domain.model.Product
import edu.itvo.kmp1.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ObserveProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.observeAll()
    }
}
