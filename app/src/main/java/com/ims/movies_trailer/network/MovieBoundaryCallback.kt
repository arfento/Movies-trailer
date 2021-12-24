package com.ims.movies_trailer.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.ims.movies_trailer.data.models.Movie
import com.ims.movies_trailer.data.models.MovieType
import com.ims.movies_trailer.database.MovieDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieBoundaryCallback(
    private val movieType: MovieType,
    private val service : NetworkApiService,
    private val movieDao : MovieDao,
    private val dispatcher : CoroutineDispatcher = Dispatchers.Default

) : PagedList.BoundaryCallback<Movie>() {
    private var lastRequestPage = 1
    private var isRequestInProgress = false
    private val _networkErrors = MutableLiveData<String>()

    //livedata of networdk errors
    val networkErrors: LiveData<String>
        get() = _networkErrors

    private val _loadingProgress = MutableLiveData<Boolean>()
    val loadingProgressBar : LiveData<Boolean>
        get() = _loadingProgress

    override fun onZeroItemsLoaded() {
//        super.onZeroItemsLoaded()
        Timber.d("onZeroItemsLoaded called")
        val scope = CoroutineScope(dispatcher)
        scope.launch {
            requestAndSaveMovie(movieType)
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
//        super.onItemAtEndLoaded(itemAtEnd)
        Timber.d("onItemAtEndLoaded called")
        val scope = CoroutineScope(dispatcher)
        scope.launch {
            requestAndSaveMovie(movieType)
        }
    }

    private suspend fun requestAndSaveMovie(movieType: MovieType = MovieType.Popular) {
        if (isRequestInProgress) return
        isRequestInProgress = true
        _loadingProgress.postValue(true)
        getMovieFromType(movieType, service, lastRequestPage, { movies ->
            movies.forEach {
                it.movieType = movieType
            }
            movieDao.insertMovies(movies)
            lastRequestPage++
            isRequestInProgress = false
            _loadingProgress.postValue(false)

        }, {error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
            _loadingProgress.postValue(false)

        })

    }


}