package com.ims.movies_trailer.ui.repository

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ims.movies_trailer.data.models.*
import com.ims.movies_trailer.database.MovieDao
import com.ims.movies_trailer.network.MovieBoundaryCallback
import com.ims.movies_trailer.network.MovieSearchBoundaryCallback
import com.ims.movies_trailer.network.NetworkApiService
import timber.log.Timber

class MovieRepository(
    val network: NetworkApiService,
    val movieDao: MovieDao
) {
    suspend fun getMovieById(movieId: Int): Movie = movieDao.getMoviesById(movieId)

    fun searchMovie(query: String): MovieResult {
        Timber.d("New query $query")
        val dataSourceFactory = movieDao.searchMovies(query)
        val boundaryCallback = MovieSearchBoundaryCallback(query, network, movieDao = movieDao)
        val networkErrors = boundaryCallback.networkErrors
        val loadingProgress = boundaryCallback.loadingProgressBar

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback).build()
        return MovieResult(data, networkErrors, loadingProgress)


    }

    fun getMovieFromType(movieType: MovieType): MovieResult {
        Timber.d("New Query $movieType")
        val dataSourceFactory = movieDao.getMovies(movieType)

        val boundaryCallback = MovieBoundaryCallback(movieType, network, movieDao = movieDao)
        val networkErrors = boundaryCallback.networkErrors
        val loadingProrgress = boundaryCallback.loadingProgressBar
        val pageConfig = PagedList.Config.Builder()
            .setPrefetchDistance(0)
            .setPageSize(DATABASE_PAGE_SIZE)
            .setInitialLoadSizeHint(DATABASE_PAGE_SIZE)
            .build()
        val data = LivePagedListBuilder(dataSourceFactory, pageConfig)
            .setBoundaryCallback(boundaryCallback).build()
        return MovieResult(data, networkErrors, loadingProrgress)

    }

    suspend fun refreshPopularMovies() {
        val result = network.movieApiService.getMovies(MovieType.Popular.type)

        try {
            if (result.isSuccessful) {
                result.body()?.results?.let {
                    it.forEach { movie ->
                        movie.movieType = MovieType.Popular
                    }
                    movieDao.insertMovies(it)

                }
            }
        } catch (error: Throwable) {
            throw MovieRefreshError("Unable to fetch movies ", error)

        }
    }

    suspend fun getMovieVideo(movieId: Int): MovieVideo?{
        val movieVideoResponse = network.movieApiService.getMovieVideo(movieId)
        Timber.d("This is the movieVideoResponse $movieVideoResponse")
        if (movieVideoResponse.isSuccessful){
            movieVideoResponse.body()?.let {
                if (!it.results.isNullOrEmpty()){
                    return it.results[0]
                }
            }
        }
        return null
    }


    companion object {
        private const val DATABASE_PAGE_SIZE = 50
    }
}
