package edu.itvo.kmp1.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.logging.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.*
import android.util.Log

actual fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                isLenient = true
                coerceInputValues = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 15000
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    // Log.e asegura que se vea en rojo y sea difícil de ignorar en Logcat
                    Log.e("NET_DEBUG", message)
                }
            }
            level = LogLevel.ALL
        }
    }
}
