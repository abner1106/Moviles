package edu.itvo.kmp1.feature.product.domain.usecase

import edu.itvo.kmp1.feature.product.domain.model.Product
import edu.itvo.kmp1.feature.product.domain.repository.ProductRepository

class SaveProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product, isUpdate: Boolean) {
        repository.save(product, isUpdate)
    }
}
