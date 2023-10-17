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
import com.nightstalker.artic.R

class NewAudioPlayerService : Service() {

    private val player: ExoPlayer by lazy { ExoPlayer.Builder(this).build() }

    private lateinit var playerNotificationManager: PlayerNotificationManager
    private lateinit var audioInfo: AudioInfo

    override fun onCreate() {
        super.onCreate()
        setupNotificationManager()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            PlayerActions.START.toString() -> {
                audioInfo = intent.getSerializableExtra(INTENT_EXTRA) as AudioInfo
                start(audioInfo)
            }
            PlayerActions.PAUSE.toString() -> pause()
            PlayerActions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(audioInfo: AudioInfo) {
        if (player.isPlaying) return
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

    private fun pause() = player.pause()

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
                return audioInfo.name.toString()
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

    enum class PlayerActions {
        START, PAUSE, STOP
    }

    companion object {
        const val CHANNEL_AUDIO_PLAYER = "channel_artic_audio_player"
        const val NOTIFICATION_ID = 200
        const val INTENT_EXTRA = "intent_extra_audio_info"
        const val TAG = "NewAudioService"
    }
}