package edu.itvo.kmp1.feature.product.data.repository

import edu.itvo.kmp1.feature.customer.core.repository.BaseInMemoryRepository
import edu.itvo.kmp1.feature.product.domain.model.Product
import edu.itvo.kmp1.feature.product.domain.repository.ProductRepository

class ProductRepositoryLocalImpl : 
    BaseInMemoryRepository<Product, String>(),
    ProductRepository {

    override fun getId(item: Product): String {
        return item.id
    }
}
