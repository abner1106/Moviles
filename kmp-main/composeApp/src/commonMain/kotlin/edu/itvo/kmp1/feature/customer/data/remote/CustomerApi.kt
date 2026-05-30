package edu.itvo.kmp1.feature.customer.data.remote

import edu.itvo.kmp1.core.network.ApiResponse
import edu.itvo.kmp1.feature.customer.data.dto.CustomerDto
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

class CustomerApi(
    private val client: HttpClient,
    private val baseUrl: String
) {
    private val json = Json { ignoreUnknownKeys = true; isLenient = true; coerceInputValues = true }

    suspend fun getCustomers(): List<CustomerDto> {
        val url = "$baseUrl/customers"
        return try {
            val response = client.get(url)
            val text = response.bodyAsText()
            println("NET_DEBUG: GET $url -> Status: ${response.status}")

            val jsonElement = json.parseToJsonElement(text)
            when (jsonElement) {
                is JsonArray -> json.decodeFromJsonElement<List<CustomerDto>>(jsonElement)
                is JsonObject -> {
                    val data = jsonElement["data"] ?: jsonElement["customers"] ?: 
                               jsonElement["result"] ?: jsonElement["items"]
                    if (data is JsonArray) json.decodeFromJsonElement<List<CustomerDto>>(data) else emptyList()
                }
                else -> emptyList()
            }
        } catch (e: Exception) {
            println("NET_DEBUG: Error fetching customers: ${e.message}")
            emptyList()
        }
    }

    suspend fun saveCustomer(customer: CustomerDto, isUpdate: Boolean) {
        try {
            val id = customer.id ?: customer.code
            val url = if (!isUpdate) "$baseUrl/customers" else "$baseUrl/customers/$id"
            val method = if (!isUpdate) HttpMethod.Post else HttpMethod.Put
            
            println("NET_DEBUG: Sending $method to $url with Body: $customer")
            
            val response = client.request(url) {
                this.method = method
                contentType(ContentType.Application.Json)
                setBody(customer)
            }
            println("NET_DEBUG: Save status=${response.status} Body=${response.bodyAsText()}")
        } catch (e: Exception) {
            println("NET_DEBUG: Network error saving customer: ${e.message}")
        }
    }

    suspend fun deleteCustomer(id: String) {
        try {
            client.delete("$baseUrl/customers/$id")
        } catch (e: Exception) {
            println("NET_DEBUG: Error deleting customer: ${e.message}")
        }
    }
}
