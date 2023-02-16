package com.nightstalker.artic.features.ticket.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.domain.model.safeCall
import com.nightstalker.artic.core.presentation.ext.viewModelCall
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.features.ticket.data.mappers.toExhibitionTicket
import com.nightstalker.artic.features.ticket.data.mappers.toLocalTicket
import com.nightstalker.artic.features.ticket.data.room.TicketDao
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket


/**
 * ViewModel для работы с билетом
 * @author Maxim Zimin
 * @created 2022-10-13
 */
class TicketDetailsViewModel(private val dao: TicketDao) : ViewModel() {
    private var _ticketContent = MutableLiveData<ContentResultState>()
    val ticketContent get() = _ticketContent

    fun getTicket(id: Long) =
        viewModelCall(
            call = {
                safeCall {
                    dao.getTicketById(id).toExhibitionTicket()
                }
            },
            contentResultState = _ticketContent
        )

    fun deleteTicket(ticketId: Long, exhibitionId: String) =
        viewModelCall(
            call = { dao.deleteTicket(ticketId = ticketId, exhibitionId = exhibitionId) },
        )

    fun saveTicket(ticket: ExhibitionTicket) = viewModelCall(
        call = { dao.insertTicket(ticket.toLocalTicket()) },
    )

}