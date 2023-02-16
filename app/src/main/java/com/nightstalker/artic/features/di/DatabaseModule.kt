package com.nightstalker.artic.features.di

import androidx.room.Room
import com.nightstalker.artic.core.data.database.AppDatabase
import com.nightstalker.artic.features.ApiConstants.AppDatabaseConstants.DB_NAME
import com.nightstalker.artic.features.ticket.data.room.TicketDao
import org.koin.dsl.module


val databaseModule = module {
    single<AppDatabase> {
        Room
            .databaseBuilder(get(), AppDatabase::class.java, DB_NAME)
            .build()
    }
    single<TicketDao> { get<AppDatabase>().getTicketsDao() }
}
