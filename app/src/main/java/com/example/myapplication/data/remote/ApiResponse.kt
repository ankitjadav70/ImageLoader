package com.example.myapplication.data.remote

sealed class ApiResponse<out T> {
    data class Success<out T>(val data : T?,val successMessage : String?=null) : ApiResponse<T>()
    data class Loading(val isLoading : Boolean =false) : ApiResponse<Nothing>()
    data class ApiError(val errorMessage : String) : ApiResponse<Nothing>()
}