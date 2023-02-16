package com.nightstalker.artic.features.audio.di

import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
val audioPresentationModule = module {
    viewModel { AudioViewModel(get()) }
}