package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.ticket.data.mappers.toLocalTicket
import com.nightstalker.artic.features.ticket.data.room.TicketDao
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import com.nightstalker.core.presentation.ext.viewModelCall

class BuyTicketViewModel(
    private val dao: TicketDao
): ViewModel() {

    fun saveTicket(ticket: ExhibitionTicket) = viewModelCall(
        call = { dao.insertTicket(ticket.toLocalTicket()) },
    )
}