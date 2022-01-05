package com.example.finaleproject.util

import com.example.finaleproject.model.detailed.GenericResponse
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseRemoteDataSource {

    suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            }
            return error(Constants.GENERIC_ERROR)
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun error(message: String): Result.Error =
        Result.Error(message)


}