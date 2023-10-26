package com.nightstalker.artic

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.nightstalker.artic.core.data.di.dispatchersModule
import com.nightstalker.artic.features.artwork.di.artworkModules
import com.nightstalker.artic.features.audio.di.audioModules
import com.nightstalker.artic.features.audio.domain.service.NewAudioPlayerService
import com.nightstalker.artic.features.di.databaseModule
import com.nightstalker.artic.features.di.networkModule
import com.nightstalker.artic.features.exhibition.di.exhibitionModules
import com.nightstalker.artic.features.qrcode.di.qrPresentationModule
import com.nightstalker.artic.features.ticket.di.ticketModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class ArticApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        startKoin {
            androidContext(this@ArticApp)
            modules(dispatchersModule)
            modules(artworkModules)
            modules(exhibitionModules)
            modules(ticketModules)
            modules(audioModules)
            modules(networkModule)
            modules(databaseModule)

            modules(qrPresentationModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val audioChannel = NotificationChannel(
            NewAudioPlayerService.CHANNEL_AUDIO_PLAYER,
            "Running audio!",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(audioChannel)

    }
}