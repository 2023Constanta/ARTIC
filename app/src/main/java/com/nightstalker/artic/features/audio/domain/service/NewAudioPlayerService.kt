package com.nightstalker.artic.features.audio.domain.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil
import com.nightstalker.artic.R
import com.nightstalker.artic.features.audio.player.AudioPlayerService
import java.io.Serializable

class NewAudioPlayerService : Service() {

    val player: ExoPlayer by lazy { ExoPlayer.Builder(this).build() }
    private lateinit var playerNotificationManager: PlayerNotificationManager

    override fun onCreate() {
        super.onCreate()
        setupNotificationManager()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> {
                val audioInfo = intent.getSerializableExtra(INTENT_EXTRA) as AudioInfo
                start(audioInfo)
                Log.d(TAG, "onStartCommand: $audioInfo")
            }
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(audioInfo: AudioInfo) {
        val notification = NotificationCompat.Builder(this, CHANNEL_AUDIO_PLAYER)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(audioInfo.name)
            .build()

        player.also {
            val mediaItem =
                MediaItem.fromUri(Uri.parse(audioInfo.audioId))
            it.setMediaItem(mediaItem)
            it.playWhenReady = true
            it.prepare()
        }

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun setupNotificationManager() {
        playerNotificationManager = PlayerNotificationManager.Builder(
            this,
            NOTIFICATION_ID,
            CHANNEL_AUDIO_PLAYER
        ).setMediaDescriptionAdapter(mediaDescriptionAdapter)
            .setNotificationListener(notificationListener)
            .build()

        playerNotificationManager.apply {
            setPlayer(player)
            setUseStopAction(true)
            setSmallIcon(android.R.drawable.ic_media_play)
        }
    }

    private val mediaDescriptionAdapter =
        object : PlayerNotificationManager.MediaDescriptionAdapter {
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

    private val notificationListener = object : PlayerNotificationManager.NotificationListener {
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

    enum class Actions {
        START, STOP
    }

    companion object {
        const val CHANNEL_AUDIO_PLAYER = "channel_artic_audio_player"
        const val NOTIFICATION_ID = 200
        const val INTENT_EXTRA = "intent_extra_audio_info"
        const val TAG = "NewAudioService"
    }
}

class AudioInfo(val name: String? = null, val audioId: String) : Serializable {
    override fun toString(): String {
        return "AudioInfo(audioId='$audioId', name=$name)"
    }
}

