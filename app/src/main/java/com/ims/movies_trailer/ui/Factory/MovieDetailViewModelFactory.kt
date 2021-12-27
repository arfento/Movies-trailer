package com.ims.movies_trailer.ui.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ims.movies_trailer.ui.repository.MovieRepository
import com.ims.movies_trailer.ui.viewmodel.MovieDetailViewModel

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(
    private val movieRepository: MovieRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MovieDetailViewModel(movieRepository) as T)
}