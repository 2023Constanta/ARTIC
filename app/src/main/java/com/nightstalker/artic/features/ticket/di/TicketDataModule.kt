package com.nightstalker.artic.features.ticket.di

import com.nightstalker.artic.features.ticket.data.TicketsRepoImpl
import com.nightstalker.artic.features.ticket.domain.repo.TicketsRepo
import com.nightstalker.artic.features.ticket.domain.usecase.TicketUseCase
import org.koin.dsl.module

val ticketDataModule = module {
    factory<TicketsRepo> { TicketsRepoImpl(get()) }
    single { TicketUseCase(get()) }
}