package com.nightstalker.artic.features.artwork.di

import com.nightstalker.artic.features.artwork.presentation.ui.detail.ArtworkDetailsViewModel
import com.nightstalker.artic.features.artwork.presentation.ui.filter.FilterArtworksViewModel
import com.nightstalker.artic.features.artwork.presentation.ui.list.ArtworksListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val artworkPresentationModule = module {
    viewModel { ArtworkDetailsViewModel(useCase = get()) }
    viewModel { ArtworksListViewModel(useCase = get()) }
    viewModel { FilterArtworksViewModel(useCase = get()) }
}