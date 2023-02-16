package com.nightstalker.artic.features.exhibition.di

import com.nightstalker.artic.features.exhibition.data.ExhibitionsRepoImpl
import com.nightstalker.artic.features.exhibition.domain.repo.ExhibitionsRepo
import com.nightstalker.artic.features.exhibition.domain.usecase.ExhibitionsUseCase
import com.nightstalker.artic.features.exhibition.data.ExhibitionsApiMapper
import org.koin.dsl.module

val exhibitionDataModule = module {
    factory { ExhibitionsApiMapper(get()) }
    factory { ExhibitionsUseCase(get()) }
    factory<ExhibitionsRepo> { ExhibitionsRepoImpl(get()) }
}