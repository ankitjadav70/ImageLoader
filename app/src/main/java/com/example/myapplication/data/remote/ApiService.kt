package com.example.myapplication.data.remote

import com.example.myapplication.data.model.DataResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/v2/content/misc/media-coverages?limit=100")
    suspend fun fetchMedia() : Response<ArrayList<DataResponseItem>>
}