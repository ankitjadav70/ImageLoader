package com.example.myapplication.data.remote

import retrofit2.HttpException
import retrofit2.Response


inline fun <T> executeApiHelper(
    responseMethod : () -> Response<T>
): ApiResponse<T> {
    return try {
        val response = responseMethod.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResponse.Success(body)
        } else {
            ApiResponse.ApiError(response.message())
        }
    } catch (e: HttpException) {
        ApiResponse.ApiError(e.message())
    } catch (e: Throwable) {
        ApiResponse.ApiError(e.message.toString())
    }
}