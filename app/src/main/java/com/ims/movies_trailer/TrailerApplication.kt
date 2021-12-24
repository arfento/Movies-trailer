package com.ims.movies_trailer

import android.app.Application
import androidx.work.*
import com.ims.movies_trailer.ui.repository.MovieRepository
import timber.log.Timber

class TrailerApplication : Application(){
    val movieRepository : MovieRepository
        get() = ServiceLocator.provideMoviesRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun setUpPopularMovieManageJob(){
        val workMaConfig = Configuration.Builder()
            .setWorkerFactory(RefreshPopularMoviesWork.Fac)
        WorkManager.initialize(this, workMaConfig)

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = PeriodicWorkRequestBuilder<RefreshPopularMoviesWork>()

        WorkManager.getInstance().enqueueUniquePeriodicWork(RefreshPopularMoviesWork::class)
    }
}