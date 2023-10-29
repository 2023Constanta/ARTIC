package com.nightstalker.artic.features.ticket.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.ticket.data.mappers.toExhibitionTicket
import com.nightstalker.artic.features.ticket.data.mappers.toLocalTicket
import com.nightstalker.artic.features.ticket.data.room.TicketDao
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import com.nightstalker.core.domain.model.safeCall
import com.nightstalker.core.presentation.ext.viewModelCall
import com.nightstalker.core.presentation.model.ContentResultState
import kotlinx.coroutines.CoroutineDispatcher


/**
 * ViewModel для работы с билетом
 * @author Maxim Zimin
 * @created 2022-10-13
 */
class TicketDetailsViewModel(
    private val dao: TicketDao,
    private val ioDispatcher: CoroutineDispatcher
    ) : ViewModel() {
    private val _ticket = MutableLiveData<ContentResultState>()
    val ticket: LiveData<ContentResultState> get() = _ticket

    private val _undoTicket = MutableLiveData<ExhibitionTicket>()
    val undoTicket: LiveData<ExhibitionTicket> get() = _undoTicket

    fun saveUndoTicket(ticket: ExhibitionTicket) {
        _undoTicket.value = ticket
    }


    fun getTicket(id: Long) =
        viewModelCall(
            call = {
                safeCall {
                    dao.getTicketById(id).toExhibitionTicket()
                }
            },
            contentResultState = _ticket
        )

    fun deleteTicket(ticketId: Long, exhibitionId: String) =
        viewModelCall(
            call = { dao.deleteTicket(ticketId = ticketId, exhibitionId = exhibitionId) },
        )

    fun saveTicket(ticket: ExhibitionTicket) = viewModelCall(
        call = { dao.insertTicket(ticket.toLocalTicket()) },
    )

}