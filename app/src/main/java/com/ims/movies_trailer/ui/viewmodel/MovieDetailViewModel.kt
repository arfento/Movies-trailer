package com.ims.movies_trailer.ui.viewmodel

import android.content.Intent
import android.net.Uri
import android.service.autofill.Transformation
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.ims.movies_trailer.data.models.Movie
import com.ims.movies_trailer.ui.repository.MovieRepository
import com.ims.movies_trailer.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class MovieDetailViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieId = MutableLiveData<Int>()

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _currentTitle = MutableLiveData<String>()
    val currentTitle: LiveData<String> = _currentTitle

    private val _openVideoPlayerEvent = MutableLiveData<Event<String>>()
    val openVideoPlayerEvent: LiveData<Event<String>> = _openVideoPlayerEvent


    private fun getMovie(id: Int) = viewModelScope.launch {
        val movie = movieRepository.getMovieById(id)
        _movie.postValue(movie)
        _currentTitle.postValue(movie.originalTitle)
    }

    fun acceptMovieId(movieId: Int) {
        _movieId.value = movieId
        getMovie(movieId)
    }

    fun openVideoPlayerEvent(id: Int) {

        viewModelScope.launch {
            val movieVideo = movieRepository.getMovieVideo(id)
            Timber.d("movieVideo got $movieVideo and url is ${movieVideo?.movieUrl}")

            movieVideo?.let {
                _openVideoPlayerEvent.value = Event(it.movieUrl)
                Timber.d("_openVideoPlayerEvent is called with ${it.movieUrl}")

            }
        }
    }


}

