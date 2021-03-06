/*
 * Developed by Michail Fotiadis.
 * Copyright (c) 2019.
 * All rights reserved.
 */

package com.michaelfotiads.xkcdreader.di

import com.michaelfotiads.xkcdreader.ui.fragment.comics.cards.CardsFragment
import com.michaelfotiads.xkcdreader.ui.fragment.comics.comics.ComicsFragment
import com.michaelfotiads.xkcdreader.ui.fragment.favourites.FavouritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun cardsFragment(): CardsFragment

    @ContributesAndroidInjector
    abstract fun comicsFragment(): ComicsFragment

    @ContributesAndroidInjector
    abstract fun favouritesFragment(): FavouritesFragment
}
