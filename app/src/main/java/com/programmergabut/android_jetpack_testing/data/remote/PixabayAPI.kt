package com.programmergabut.android_jetpack_testing.data.remote

import com.programmergabut.android_jetpack_testing.BuildConfig
import com.programmergabut.android_jetpack_testing.data.remote.responses.ImageResponse
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