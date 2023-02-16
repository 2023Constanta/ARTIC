package com.nightstalker.artic.features.ticket.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nightstalker.artic.features.ApiConstants.AppDatabaseConstants.DB_TABLE_TICKETS
import java.util.Date

/**
 * Сущность билета
 * @author Tamerlan Mamukhov on 2022-09-18
 */
@Entity(
    tableName = DB_TABLE_TICKETS,
    indices = [Index(
        value = ["exhibition_id"],
        unique = true
    )]
)
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "exhibition_id")
    val exhibitionId: String = "",
    @ColumnInfo(name = "image_url")
    val imageUrl: String = "",
    @ColumnInfo(name = "gallery_id")
    val galleryId: Int = 0,
    @ColumnInfo(name = "gallery_title")
    val galleryTitle: String = "",
    @ColumnInfo(name = "aic_end_at")
    val aicEndAt: String = "",
    @ColumnInfo(name = "aic_start_at")
    val aicStartAt: String = "",
    @ColumnInfo(name = "shortDescription")
    val shortDescription: String = "",
    @ColumnInfo(name = "number_of_persons")
    val numberOfPersons: Int = 1,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long = Date().time,
)
