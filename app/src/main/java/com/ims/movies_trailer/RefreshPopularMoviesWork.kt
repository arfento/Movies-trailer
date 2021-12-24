package com.ims.movies_trailer

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ims.movies_trailer.ui.repository.MovieRepository

class RefreshPopularMoviesWork(
    private val context: Context,
    params : WorkerParameters,
    private val movieRepository: MovieRepository
) : CoroutineWorker(context, params){
    private val notificationManager = ContextCompat.getSystemService(
        con
    )

}
