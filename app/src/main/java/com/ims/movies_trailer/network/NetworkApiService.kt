package com.ims.movies_trailer.network

import com.ims.movies_trailer.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public const val apiKey: String = BuildConfig.API_KEY
//public const val apiKey: String = "4a329289acb21054e19231fa30afe4f3"


object NetworkApiService {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/w185"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val movieApiService = retrofit.create(MovieApiService::class.java)
}

