package edu.itvo.kmp1.feature.product.data.remote

import edu.itvo.kmp1.core.network.ApiResponse
import edu.itvo.kmp1.feature.product.data.dto.ProductDto
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

class ProductApi(
    private val client: HttpClient,
    private val baseUrl: String
) {
    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    suspend fun getProducts(): List<ProductDto> {
        val url = "$baseUrl/products"
        return try {
            val response = client.get(url)
            val text = response.bodyAsText()
            println("NET_DEBUG: GET $url Status=${response.status}")
            
            val jsonElement = json.parseToJsonElement(text)
            when (jsonElement) {
                is JsonArray -> json.decodeFromJsonElement<List<ProductDto>>(jsonElement)
                is JsonObject -> {
                    val data = jsonElement["data"] ?: jsonElement["products"] ?: jsonElement["result"] ?: jsonElement["items"]
                    if (data is JsonArray) json.decodeFromJsonElement<List<ProductDto>>(data) else emptyList()
                }
                else -> emptyList()
            }
        } catch (e: Exception) {
            println("NET_DEBUG: Error parsing products: ${e.message}")
            emptyList()
        }
    }

    suspend fun saveProduct(product: ProductDto, isUpdate: Boolean) {
        try {
            // Si es nuevo usamos /products (POST), si es edición usamos /products/{id} (PUT)
            val id = product.id ?: product.code
            val url = if (!isUpdate) "$baseUrl/products" else "$baseUrl/products/$id"
            val method = if (!isUpdate) HttpMethod.Post else HttpMethod.Put
            
            println("NET_DEBUG: Sending $method to $url with Body: $product")
            
            val response = client.request(url) {
                this.method = method
                contentType(ContentType.Application.Json)
                setBody(product)
            }
            println("NET_DEBUG: Save product status=${response.status} Body=${response.bodyAsText()}")
        } catch (e: Exception) {
            println("NET_DEBUG: Network error saving product: ${e.message}")
        }
    }

    suspend fun deleteProduct(id: String) {
        try {
            val response = client.delete("$baseUrl/products/$id")
            println("NET_DEBUG: Delete status=${response.status}")
        } catch (e: Exception) {
            println("NET_DEBUG: Error deleting product: ${e.message}")
        }
    }
}
