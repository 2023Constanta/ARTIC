package com.nightstalker.artic.features.audio.player

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil
import com.nightstalker.artic.MainActivity
import com.nightstalker.artic.R
import com.nightstalker.artic.features.ApiConstants.EXTRA_AUDIO_URL

/**
 * Сервис проигрывателя аудио
 *
 * @author Tamerlan Mamukhov on 2023-01-12
 */
class NewAudioPlayerService : Service() {
    private val binder: Binder = NewAudioPlayerServiceBinder()

    val player: ExoPlayer by lazy {
        ExoPlayer.Builder(this).build()
    }

    // Для плеера
    private lateinit var playerNotificationManager: PlayerNotificationManager

    override fun onCreate() {
        super.onCreate()
        setupNotificationManager()
        Log.d(TAG, "onCreate: ")
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val audioUrl = intent?.getStringExtra(EXTRA_AUDIO_URL)

        Log.d(TAG, "onStartCommand: $audioUrl")
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.btn_star)
            .setContentIntent(pendingIntent)
            .build()

        player.also {
            val mediaItem =
                MediaItem.fromUri(Uri.parse(audioUrl))
            it.setMediaItem(mediaItem)
            it.playWhenReady = true
            it.prepare()
        }

        startForeground(1, notification)

        return START_NOT_STICKY
    }


//    override fun onDestroy() {
//        super.onDestroy()
//        playerNotificationManager.setPlayer(null)
//        player.release()
//        Log.d(TAG, "onDestroy: ")
//    }

    inner class NewAudioPlayerServiceBinder : Binder() {
        fun getService(): NewAudioPlayerService {
            return this@NewAudioPlayerService
        }
    }

    private fun setupNotificationManager() {
        NotificationUtil.createNotificationChannel(
            this,
            FOREGROUND_CHANNEL_ID,
            R.string.audio_channel_name,
            R.string.audio_channel_description,
            NotificationUtil.IMPORTANCE_DEFAULT
        )
        playerNotificationManager = PlayerNotificationManager.Builder(
            this,
            NOTIFICATION_ID,
            FOREGROUND_CHANNEL_ID
        ).setMediaDescriptionAdapter(setMediaAdapter())
            .setNotificationListener(setNotifMan())
            .build()

        playerNotificationManager.apply {
            setPlayer(player)
            setSmallIcon(android.R.drawable.ic_media_play)
        }
    }

    private fun setMediaAdapter(): PlayerNotificationManager.MediaDescriptionAdapter {
        return object : PlayerNotificationManager.MediaDescriptionAdapter {
            override fun getCurrentContentTitle(player: Player): CharSequence {
                return "Test"
            }

            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                return null
            }

            override fun getCurrentContentText(player: Player): CharSequence? {
                return null
            }

            override fun getCurrentLargeIcon(
                player: Player,
                callback: PlayerNotificationManager.BitmapCallback
            ): Bitmap? {
                return null
            }

        }
    }

    private fun setNotifMan(): PlayerNotificationManager.NotificationListener {
        return object : PlayerNotificationManager.NotificationListener {
            override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                stopForeground(true)
            }

            override fun onNotificationPosted(
                notificationId: Int,
                notification: Notification,
                ongoing: Boolean
            ) {
                startForeground(notificationId, notification)
            }
        }
    }

    companion object {

        const val FOREGROUND_CHANNEL_ID = "foreground_channel_id"
        const val NOTIFICATION_ID = 200

        const val CANCEL_ACTION = "Cancel_Notification"

        private const val TAG = "NewAudioPlayerService"

        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, NewAudioPlayerService::class.java)
        }
    }
}