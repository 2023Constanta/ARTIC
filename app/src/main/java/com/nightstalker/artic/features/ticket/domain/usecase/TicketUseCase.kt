package com.nightstalker.artic.features.ticket.domain.usecase

import com.nightstalker.artic.core.domain.model.ResultState
import com.nightstalker.artic.core.domain.model.safeCall
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import com.nightstalker.artic.features.ticket.domain.repo.TicketsRepo

class TicketUseCase(
    private val repo: TicketsRepo
) {
    suspend fun getAllTickets(): ResultState<List<ExhibitionTicket>> =
        safeCall { repo.getTickets() }

    suspend fun getTicketById(id: Long) = safeCall {
        repo.getTicketById(id)
    }

    suspend fun deleteTicket(ticketId: Long, exhibitionId: String) = safeCall {
        repo.deleteTicket(ticketId, exhibitionId)
    }

    suspend fun saveTicket(ticket: ExhibitionTicket) = safeCall {
        repo.saveTicket(ticket)
    }
}