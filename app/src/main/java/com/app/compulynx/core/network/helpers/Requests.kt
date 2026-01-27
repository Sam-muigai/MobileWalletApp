package com.app.compulynx.core.network.helpers

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

suspend inline fun <reified T, reified M> HttpClient.postRequest(
    data: T,
    urlString: String,

): NetworkResult<M> {
    return safeApiCall {
        post(urlString = urlString) {
            setBody(data)
        }
    }
}


suspend inline fun <reified T> HttpClient.getRequest(
    urlString: String,
): NetworkResult<T> {
    return safeApiCall {
        get(urlString = urlString)
    }
}