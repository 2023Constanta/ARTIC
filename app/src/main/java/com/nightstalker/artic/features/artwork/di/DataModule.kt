package com.nightstalker.artic.features.artwork.di

import com.nightstalker.artic.features.artwork.data.ArtworkRepoImpl
import com.nightstalker.artic.features.artwork.domain.repo.ArtworkRepo
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase
import com.nightstalker.artic.features.artwork.data.ArtworksApiMapper
import org.koin.dsl.module

val artworkDataModule = module {
    factory { ArtworksApiMapper(get()) }
    factory { ArtworksUseCase(get()) }
    factory<ArtworkRepo> { ArtworkRepoImpl(get()) }
}