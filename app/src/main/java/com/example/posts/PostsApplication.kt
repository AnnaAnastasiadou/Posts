package com.example.posts

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.posts.worker.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Application class responsible for initializing Hilt and configuring
 * WorkManager to run the SyncWorker periodically every 15 minutes.
 *
 * - Hilt is initialized through @HiltAndroidApp.
 * - The worker is scheduled as unique periodic work.
 * - The worker only runs when the device has internet access.
 */
@HiltAndroidApp
class PostsApplication : Application(), Configuration.Provider {
    /**
     * Factory used by WorkManager to create workers that use Hilt dependency injection.
     *
     * WorkManager normally creates workers itself, but since SyncWorker has
     * injected dependencies (like PostRepository), it must use HiltWorkerFactory
     * to construct the worker correctly.
     */
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        scheduleSyncWork()
    }

    /**
     * Schedules the background sync worker.
     *
     * This function creates a WorkManager request that will run SyncWorker
     * every 15 minutes to fetch posts from the API and store them in the
     * local Room database.
     */
    private fun scheduleSyncWork() {
        /**
         *
         * Constraints define conditions that must be met before the worker runs
         * Require the device to have an active internet connection
         * since the worker fetches data from a remote API
         *
         */

        val constraints =
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        /**
         *
         * PeriodicWorkRequest defines a repeating background job.
         * The worker runs every 15 minutes
         *
         */
        val syncWorkRequest =
            PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        /**
         * Tell WorkManager to schedule
         *
         * Enqueue the worker as UNIQUE periodic work.
         * "sync_posts_work" → unique identifier for this task.
         *
         * ExistingPeriodicWorkPolicy.KEEP ensures:
         * - If the worker is already scheduled, it will NOT be scheduled again.
         * - This prevents duplicate background jobs from running.
         */
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "sync_posts_work",
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorkRequest
        )
    }
}