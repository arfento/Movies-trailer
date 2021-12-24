package com.ims.movies_trailer.network

import com.ims.movies_trailer.data.models.Movie
import com.ims.movies_trailer.data.models.MovieVideoResponse
import com.ims.movies_trailer.data.models.ResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/{type}?api_key=$apiKey")
    suspend fun getMovies(@Path("type") type: String, @Query("page")page: Int = 1 ): Response<ResultResponse<Movie>>

    @GET("search/movie?api_key=$apiKey")
    suspend fun searchMovies(@Query("query")query:String, @Query("page") page: Int = 1, @Query("include_adult") includeAdult :Boolean = false): Response<ResultResponse<Movie>>

    @GET("movie/{movie_id}/videos?api_key=$apiKey")
    suspend fun getMovieVideo(@Path("movie_id") movieId: Int ): Response<MovieVideoResponse>

}