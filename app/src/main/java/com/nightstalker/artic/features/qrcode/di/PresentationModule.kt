package com.nightstalker.artic.features.qrcode.di

import com.nightstalker.artic.features.qrcode.presentation.QrCodeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val qrPresentationModule = module {

    viewModel { QrCodeViewModel(get()) }
}