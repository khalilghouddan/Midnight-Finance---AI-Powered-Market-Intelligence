package com.example.analyse_newss.outil

import retrofit2.http.GET
import retrofit2.http.Query

interface GNewsApiService {
    @GET("search")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("lang") lang: String = "en",
        @Query("token") token: String,
        @Query("from") fromDate: String? = null
    ): GNewsResponse
}
