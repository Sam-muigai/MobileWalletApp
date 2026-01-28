package com.app.compulynx.core.network.helpers

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

sealed class NetworkResult<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error(message: String) : NetworkResult<Nothing>(message = message)
}


suspend inline fun <reified T> safeApiCall(
    call: suspend () -> HttpResponse
): NetworkResult<T> {
    return try {
        val response = call.invoke()
        when {
            response.status.value >= 200 && response.status.value < 299 -> NetworkResult.Success(
                response.body()
            )

            response.status.value >= 400 && response.status.value < 499 -> {
                NetworkResult.Error(
                     "Something went wrong!!"
                )
            }

            else -> NetworkResult.Error("Server error occurred. Please try again later")
        }
    } catch (e: Exception) {
        NetworkResult.Error(e.message ?: "Something went wrong!!")
    }
}