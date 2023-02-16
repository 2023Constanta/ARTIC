package com.nightstalker.artic.features.ticket.presentation.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.presentation.ext.viewModelCall
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.features.ticket.domain.usecase.TicketUseCase


/**
 * ViewModel для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */
class TicketsViewModel(private val useCase: TicketUseCase) : ViewModel() {

    private var _ticketsContent = MutableLiveData<ContentResultState>()
    val ticketsContent get() = _ticketsContent

    fun getAllTickets() = viewModelCall(
        call = { useCase.getAllTickets() },
        contentResultState = _ticketsContent
    )

}