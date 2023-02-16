package com.nightstalker.artic.features.ticket.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nightstalker.artic.features.ApiConstants.AppDatabaseConstants.DB_TABLE_TICKETS

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: TicketEntity)

    @Query("DELETE FROM $DB_TABLE_TICKETS WHERE id = :ticketId OR exhibition_id = :exhibitionId ")
    suspend fun deleteTicket(ticketId: Long, exhibitionId: String)

    @Update
    suspend fun updateTicket(ticket: TicketEntity)

    @Query("SELECT * FROM $DB_TABLE_TICKETS  WHERE id = :key")
    suspend fun getTicketById(key: Long): TicketEntity

    @Query("SELECT * FROM $DB_TABLE_TICKETS")
    suspend fun getAllTickets(): List<TicketEntity>

    @Query("SELECT * FROM $DB_TABLE_TICKETS WHERE upper(title) like  upper('%'||:term||'%')  ")
    fun searchTicketsByTerm(term: String): List<TicketEntity>

}