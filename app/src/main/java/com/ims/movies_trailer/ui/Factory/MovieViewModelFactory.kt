package com.ims.movies_trailer.ui.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ims.movies_trailer.ui.repository.MovieRepository
import com.ims.movies_trailer.ui.viewmodel.MoviesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory (
    private val movieRepository: MovieRepository
) : ViewModelProvider.NewInstanceFactory() {
    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MoviesViewModel(movieRepository) as T)
}