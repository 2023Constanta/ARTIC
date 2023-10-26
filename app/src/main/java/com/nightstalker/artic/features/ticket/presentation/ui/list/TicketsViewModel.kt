package com.nightstalker.artic.features.ticket.presentation.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.ticket.domain.usecase.TicketUseCase
import com.nightstalker.core.presentation.ext.viewModelCall
import com.nightstalker.core.presentation.model.ContentResultState
import kotlinx.coroutines.CoroutineDispatcher


/**
 * ViewModel для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */
class TicketsViewModel(
    private val useCase: TicketUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _ticketsContent = MutableLiveData<ContentResultState>()
    val ticketsContent get() = _ticketsContent

    fun getAllTickets() {
        viewModelCall(
            dispatcher = ioDispatcher,
            call = { useCase.getAllTickets() },
            contentResultState = _ticketsContent
        )
    }

    fun loadTickets() {
        if (_ticketsContent.value is ContentResultState.Loading) {
            getAllTickets()
        }
    }

}