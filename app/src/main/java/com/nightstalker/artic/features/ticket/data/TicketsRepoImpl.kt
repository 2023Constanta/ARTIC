package com.nightstalker.artic.features.ticket.data


import com.nightstalker.artic.features.ticket.data.mappers.toExhibitionTicket
import com.nightstalker.artic.features.ticket.data.mappers.toLocalTicket
import com.nightstalker.artic.features.ticket.data.room.TicketDao
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import com.nightstalker.artic.features.ticket.domain.repo.TicketsRepo

class TicketsRepoImpl(
    private val dao: TicketDao
) : TicketsRepo {
    override suspend fun getTicketById(id: Long): ExhibitionTicket =
        dao.getTicketById(id).toExhibitionTicket()

    override suspend fun getTickets(): List<ExhibitionTicket> =
        dao.getAllTickets().map { it.toExhibitionTicket() }

    override suspend fun deleteTicket(ticketId: Long, exhibitionId: String) {
        dao.deleteTicket(ticketId, exhibitionId)
    }

    override suspend fun saveTicket(ticket: ExhibitionTicket) {
        dao.insertTicket(ticket.toLocalTicket())
    }
}