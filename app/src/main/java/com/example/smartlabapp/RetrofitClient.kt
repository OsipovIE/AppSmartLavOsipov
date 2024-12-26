// RetrofitClient.kt
package com.example.smartlabapp.api

import SupabaseApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://gvadfakngncjjfxskqqj.supabase.co"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imd2YWRmYWtuZ25jampmeHNrcXFqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzUxMzAyMzgsImV4cCI6MjA1MDcwNjIzOH0.cqKH7CMXA4xzIusrMl02PwvSyVVakayBNoWYK3Lkypw"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val supabaseApi: SupabaseApi by lazy {
        retrofit.create(SupabaseApi::class.java)
    }

    fun getApiKey(): String {
        return "Bearer $API_KEY"
    }
}
