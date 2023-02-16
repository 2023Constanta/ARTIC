package com.nightstalker.artic.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nightstalker.artic.features.ticket.data.room.TicketDao
import com.nightstalker.artic.features.ticket.data.room.TicketEntity

@Database(entities = [TicketEntity::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getTicketsDao(): TicketDao
}