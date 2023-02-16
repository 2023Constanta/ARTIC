package com.nightstalker.artic.features.audio.di

import com.nightstalker.artic.features.audio.data.AudioRepoImpl
import com.nightstalker.artic.features.audio.domain.repo.AudioRepo
import com.nightstalker.artic.features.audio.domain.usecase.AudioUseCase
import com.nightstalker.artic.features.audio.data.AudioApiMapper
import org.koin.dsl.module

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
val audioDataModule = module {
    factory { AudioApiMapper(get()) }
    factory { AudioUseCase(get()) }
    factory<AudioRepo> { AudioRepoImpl(get()) }
}