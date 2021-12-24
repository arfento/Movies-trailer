package com.ims.movies_trailer.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.ims.movies_trailer.R
import com.ims.movies_trailer.data.models.Movie
import com.ims.movies_trailer.data.models.MovieResult
import com.ims.movies_trailer.data.models.MovieType
import com.ims.movies_trailer.ui.repository.MovieRepository
import com.ims.movies_trailer.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber


@FlowPreview
@ExperimentalCoroutinesApi
class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieTypeSelected = MutableLiveData<MovieType>(MovieType.Popular)
    private val queryLiveData = MutableLiveData<String>()

    private val movieResult: LiveData<MovieResult> =
        Transformations.map(movieTypeSelected) { movieType ->
            movieRepository.getMovieFromType(movieType)
        }

    private val movieSearchResult: LiveData<MovieResult> =
        Transformations.map(queryLiveData) { query ->
            movieRepository.searchMovie(query)
        }

    var movies : LiveData<PagedList<Movie>> = Transformations.switchMap(movieResult){
        it.data
    }
    var networkErrors: LiveData<String> = Transformations.switchMap(movieResult){
        it.networkErrors
    }
    var loadingSpinner: LiveData<Boolean> = Transformations.switchMap(movieResult){
        it.loadingData
    }
    var searchMovies: LiveData<PagedList<Movie>> = Transformations.switchMap(movieSearchResult){
        it.data
    }
    var searchNetworkErrors: LiveData<String> = Transformations.switchMap(movieSearchResult){
        it.networkErrors
    }
    private val _currentTitle = MutableLiveData<Int>()
    val currentTitle : LiveData<Int> = _currentTitle

    /**
     * Search repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

//    private val _networkError =  MutableLiveData<Event<String>>();
//    val networkError: LiveData<Event<String>>
//    get ()= _networkError


    private val _openDetailsEvent = MutableLiveData<Event<Int>>()
    val openDetailsEvent: LiveData<Event<Int>> = _openDetailsEvent

    init {
        setTitle(R.string.popular_title)
    }

    fun refreshType(){
        movieTypeSelected.postValue(movieTypeSelected.value)
    }

    fun changeMovieType(movieType: MovieType){
        movieTypeSelected.postValue(movieType)
        when(movieType){
            MovieType.Popular -> setTitle(R.string.popular_title)
            MovieType.TopRated -> setTitle(R.string.top_rated_title)
            MovieType.NowPlaying -> setTitle(R.string.now_playing_title)
            MovieType.UpComing -> setTitle(R.string.upcoming_title)
        }
    }

    fun openMovieDetails(id : Int){
        _openDetailsEvent.value = Event(id)
        Timber.d("OpenMovieDetails is called with $id")
    }

    private fun setTitle(@StringRes titleString: Int) {
        _currentTitle.value = titleString

    }
}