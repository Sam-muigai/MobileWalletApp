package com.app.compulynx.data.helpers

import com.app.compulynx.core.network.helpers.NetworkResult
import kotlinx.coroutines.CancellationException

inline fun <reified T, reified K> NetworkResult<T>.mapResult(
    transformer: (T?) -> K
): Result<K> {
    return try {
        when (val response = this) {
            is NetworkResult.Error -> Result.failure(Exception(response.message))
            is NetworkResult.Success -> Result.success(transformer(response.data))
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}