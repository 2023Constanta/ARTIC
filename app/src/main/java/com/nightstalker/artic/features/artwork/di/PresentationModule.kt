package com.nightstalker.artic.features.artwork.di

import com.nightstalker.artic.core.data.di.Constants
import com.nightstalker.artic.features.artwork.presentation.ui.detail.ArtworkDetailsViewModel
import com.nightstalker.artic.features.artwork.presentation.ui.filter.FilterArtworksViewModel
import com.nightstalker.artic.features.artwork.presentation.ui.list.ArtworksListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val artworkPresentationModule = module {

    viewModel {
        ArtworkDetailsViewModel(
            useCase = get(),
            ioDispatcher = get(named(Constants.DISPATCHER_IO)),
        )
    }
    viewModel {
        ArtworksListViewModel(
            useCase = get(),
            ioDispatcher = get(named(Constants.DISPATCHER_IO)),
            mainDispatcher = get(named(Constants.DISPATCHER_MAIN))
        )
    }

    viewModel {
        FilterArtworksViewModel(
            useCase = get(),
            ioDispatcher = get(named(Constants.DISPATCHER_IO)),
            mainDispatcher = get(named(Constants.DISPATCHER_MAIN))
        )
    }
}