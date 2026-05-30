package edu.itvo.kmp1.feature.product.domain.usecase

import edu.itvo.kmp1.feature.product.domain.repository.ProductRepository

class DeleteProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteById(id)
    }
}
