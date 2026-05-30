package edu.itvo.kmp1.feature.product.data.datasource.remote

import edu.itvo.kmp1.feature.product.data.dto.ProductDto
import edu.itvo.kmp1.feature.product.data.remote.ProductApi

class ProductRemoteDataSource(
    private val api: ProductApi
) {
    suspend fun getProducts(): List<ProductDto> {
        return api.getProducts()
    }

    suspend fun saveProduct(product: ProductDto, isUpdate: Boolean) {
        api.saveProduct(product, isUpdate)
    }

    suspend fun deleteProduct(id: String) {
        api.deleteProduct(id)
    }
}
