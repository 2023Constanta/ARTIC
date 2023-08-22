package com.nightstalker.artic.features.exhibition.di

import com.nightstalker.artic.features.exhibition.presentation.ui.detail.BuyTicketViewModel
import com.nightstalker.artic.features.exhibition.presentation.ui.detail.ExhibitionDetailsViewModel
import com.nightstalker.artic.features.exhibition.presentation.ui.list.ExhibitionsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val exhibitionPresentationModule = module {
    viewModel { ExhibitionsListViewModel(get()) }
    viewModel { ExhibitionDetailsViewModel(get()) }
    viewModel { BuyTicketViewModel(get()) }
}