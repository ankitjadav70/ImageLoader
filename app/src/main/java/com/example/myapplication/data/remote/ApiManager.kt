package com.example.myapplication.data.remote

import com.example.myapplication.data.model.DataResponseItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager {

    private val apiService: ApiService by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)

        okHttpClient.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        return@lazy Retrofit.Builder()
            .baseUrl("https://acharyaprashant.org/")
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }



    suspend fun fetchMedia(): ApiResponse<ArrayList<DataResponseItem>>{
        return executeApiHelper {
            apiService.fetchMedia()
        }
    }

}