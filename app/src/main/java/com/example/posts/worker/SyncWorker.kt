package com.example.posts.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.posts.data.repository.PostRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * WorkManager worker responsible for periodically syncing posts
 * from the remote API into the local Room database.
 */
@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val postRepository: PostRepository
) : CoroutineWorker(appContext, workerParameters) {

    companion object {
        const val CHANNEL_ID = "sync_channel"
        const val NOTIFICATION_ID  = 1
    }
    override suspend fun doWork(): Result {
        return try {
            postRepository.syncPosts()
            showNotification("Sync completed", "Posts refreshed successfully")
            // Inform WorkManager that the task finished successfully
            Result.success()
        } catch (e: Exception) {
            showNotification("Sync failed", "Will retry later")
            // If something fails WorkManager will retry the task later
            Result.retry()
        }
    }

    /**
     * Displays a notification showing the result of the sync operation.
     *
     * NotificationManager is the Android system service responsible
     * for displaying notifications to the user.
     */
    private fun showNotification(title: String, message: String) {
        // Get the system NotificationManager service
        val manager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /**
         * Android 8.0+ requires notifications to belong to a channel.
         * A channel groups similar notifications and allows users
         * to control their behavior (sound, vibration, etc.).
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Sync Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            // Register the channel with the system.
            // If the channel already exists, Android ignores this call.
            manager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            // Required small icon (Android will crash if omitted)
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        manager.notify(NOTIFICATION_ID, notification)
    }
}