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
class MovieDetailViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {


}
