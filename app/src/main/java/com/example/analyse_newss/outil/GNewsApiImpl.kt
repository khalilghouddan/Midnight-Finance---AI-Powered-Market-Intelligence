package com.example.analyse_newss.outil

import android.util.Log
import com.example.analyse_newss.model.News
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GNewsApiImpl {
    private const val BASE_URL = "https://gnews.io/api/v4/"
    private const val API_KEY = "___________________________________"

    private val apiService: GNewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GNewsApiService::class.java)
    }

    suspend fun getNews(): List<News> {
        return try {
            Log.d("GNewsApi", "Fetching news...")

            val response = apiService.searchNews(
                query = "finance OR market OR stock",
                token = API_KEY
            )

            Log.d("GNewsApi", "Fetched ${response.articles.size} articles")

            response.articles.map { article ->
                News(
                    title = article.title,
                    source = article.source.name,
                    description = article.description,
                    url = article.url
                )
            }
        } catch (e: Exception) {
            Log.e("GNewsApi", "Error fetching news", e)
            // Return an error card so the user can see what went wrong
            listOf(
                News(
                    title = "Error: ${e.message}",
                    source = "System",

                    description = "Failed to fetch news. Please check your internet connection or API key quota.",
                    url = "about:blank"
                )
            )
        }
    }
}
