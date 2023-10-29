package com.nightstalker.artic.core.data.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule = module {

    single(named(Constants.DISPATCHER_IO)) {
        Dispatchers.IO
    }

    single<CoroutineDispatcher>(named(Constants.DISPATCHER_MAIN)) {
        Dispatchers.Main
    }
}