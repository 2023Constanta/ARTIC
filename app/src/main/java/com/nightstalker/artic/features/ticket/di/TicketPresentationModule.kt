package com.nightstalker.artic.features.ticket.di

import com.nightstalker.artic.core.data.di.Constants
import com.nightstalker.artic.features.ticket.presentation.ui.detail.TicketDetailsViewModel
import com.nightstalker.artic.features.ticket.presentation.ui.list.TicketsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ticketPresentationModule = module {
    viewModel { TicketsViewModel(get(), ioDispatcher = get(named(Constants.DISPATCHER_IO)),) }
    viewModel { TicketDetailsViewModel(get(), ioDispatcher = get(named(Constants.DISPATCHER_IO)),) }
}