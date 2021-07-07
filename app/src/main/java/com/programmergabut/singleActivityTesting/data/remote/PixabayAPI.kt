package com.programmergabut.singleActivityTesting.data.remote

import com.programmergabut.singleActivityTesting.BuildConfig
import com.programmergabut.singleActivityTesting.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}