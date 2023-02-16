package com.nightstalker.artic.features.ticket.domain.repo

import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket


/**
 * Репозиторий для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */
interface TicketsRepo {
    suspend fun getTicketById(id: Long): ExhibitionTicket
    suspend fun getTickets(): List<ExhibitionTicket>
    suspend fun deleteTicket(ticketId: Long, exhibitionId: String)
    suspend fun saveTicket(ticket: ExhibitionTicket)
}