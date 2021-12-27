package com.ims.movies_trailer.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.ims.movies_trailer.data.models.Movie
import com.ims.movies_trailer.database.MovieDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieSearchBoundaryCallback (
    private val query: String,
    private val service: NetworkApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val movieDao: MovieDao
): PagedList.BoundaryCallback<Movie>(){

    private var searchRequestedPage = 1

    private var isRequestInProgress = false
    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    private val _loadingProgress = MutableLiveData<Boolean>()
    val loadingProgress: LiveData<Boolean> get() = _loadingProgress
    override fun onZeroItemsLoaded() {
        val  scope = CoroutineScope(dispatcher)
        scope.launch {
            searchAndSaveMovie(query)
        }    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        val  scope = CoroutineScope(dispatcher)
        scope.launch {
            searchAndSaveMovie(query)
        }     }

    private suspend fun searchAndSaveMovie(query: String){
        if (isRequestInProgress) return
        isRequestInProgress = true
        _loadingProgress.postValue(true)

        searchMovie(query, service, searchRequestedPage, { movies->
            searchRequestedPage++
            isRequestInProgress = false
            _loadingProgress.postValue(false)

        }, {error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
            _loadingProgress.postValue(false)

        })

    }
}