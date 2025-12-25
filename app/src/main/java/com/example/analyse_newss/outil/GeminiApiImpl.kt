package com.example.analyse_newss.outil

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GeminiApiImpl {
    // TODO: Replace with your actual Gemini API Key from https://aistudio.google.com/
    private const val API_KEY = "---------------API----------"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val apiService: GeminiApiService by lazy {
        val client = okhttp3.OkHttpClient.Builder()
            .readTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
            .build()

        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(GeminiApiService::class.java)
    }

    suspend fun getChatResponse(userMessage: String): String {
        return kotlinx.coroutines.withContext(Dispatchers.IO) {
            var retryCount = 0
            val maxRetries = 3
            
            while (retryCount < maxRetries) {
                try {
                    Log.d("GeminiApi", "Sending request: $userMessage (Attempt ${retryCount + 1})")
    
                    val request = GeminiRequest(
                        contents = listOf(
                            GeminiContent(parts = listOf(GeminiPart(text = userMessage)))
                        )
                    )
    
                    val response = apiService.generateContent(
                        model = "gemini-2.5-flash",
                        apiKey = API_KEY,
                        request = request
                    )
                    
                    val responseText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    Log.d("GeminiApi", "Received response: $responseText")
                    
                    return@withContext responseText ?: "No response from Gemini."
                    
                } catch (e: retrofit2.HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    Log.e("GeminiApi", "HTTP Error: $errorBody", e)
                    
                    if (e.code() == 503) {
                        retryCount++
                        if (retryCount < maxRetries) {
                            val delayMs = 1000L * retryCount // Exponential-ish backoff: 1s, 2s...
                            Log.w("GeminiApi", "503 Overloaded. Retrying in ${delayMs}ms...")
                            kotlinx.coroutines.delay(delayMs)
                            continue
                        } else {
                            return@withContext "Error: Gemini is currently overloaded. Please try again later."
                        }
                    }
                    return@withContext "Error: ${e.code()} - $errorBody"
                    
                } catch (e: Exception) {
                    Log.e("GeminiApi", "Error fetching Gemini response", e)
                    return@withContext "Error: ${e.localizedMessage}"
                }
            }
            "Error: Max retries exceeded"
        }
    }

    suspend fun analyzeNews(title: String, description: String, source: String): String {
        return getChatResponse(
            """
            Act as a financial analyst. Analyze this news:
            Title: $title
            Source: $source
            Context: $description
            
            Provide a response in this format:
            
            **Summary**: [Brief summary]
            
            **Investment Recommendation**: [Actionable advice]
            """.trimIndent()
        )
    }
}
