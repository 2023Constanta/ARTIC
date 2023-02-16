package com.nightstalker.artic.features.ticket.di

import com.nightstalker.artic.features.ticket.presentation.ui.detail.TicketDetailsViewModel
import com.nightstalker.artic.features.ticket.presentation.ui.list.TicketsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ticketPresentationModule = module {
    viewModel { TicketsViewModel(get()) }
    viewModel { TicketDetailsViewModel(get()) }
}