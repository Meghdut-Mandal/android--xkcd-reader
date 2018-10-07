/*
 * Developed by Michail Fotiadis on 07/10/18 18:03.
 * Last modified 07/10/18 18:03.
 * Copyright (c) 2018. All rights reserved.
 *
 *
 */

package com.michaelfotiads.xkcdreader.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.michaelfotiads.xkcdreader.BuildConfig
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun providesContext(application: Application): Context {
        return application
    }

    @Provides
    internal fun providesDebugFlag(): Boolean {
        return BuildConfig.DEBUG
    }

    @Provides
    @Named("base-url")
    internal fun providesBaseUrl(): String {
        return "https://xkcd.com"
    }

    @Provides
    internal fun providesGson(): Gson {
        return GsonBuilder().setPrettyPrinting().create()
    }

    @Provides
    internal fun providesExecutionScheduler() = Schedulers.io()
}