/*
 * Developed by Michail Fotiadis on 07/10/18 18:03.
 * Last modified 07/10/18 18:03.
 * Copyright (c) 2018. All rights reserved.
 *
 *
 */

package com.michaelfotiads.xkcdreader.net.loader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michaelfotiads.xkcdreader.net.api.ComicApi
import com.michaelfotiads.xkcdreader.net.api.model.ComicStrip
import com.michaelfotiads.xkcdreader.net.loader.error.DataSourceError
import com.michaelfotiads.xkcdreader.net.loader.error.DataSourceErrorKind
import com.michaelfotiads.xkcdreader.net.loader.error.mapper.RetrofitErrorMapper
import com.michaelfotiads.xkcdreader.net.resolver.NetworkResolver
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit

class NetworkLoader(
    retrofit: Retrofit,
    private val networkResolver: NetworkResolver,
    private val errorMapper: RetrofitErrorMapper,
    private val scheduler: Scheduler
) {

    private val compositeDisposable = CompositeDisposable()
    private var latestComicCall: Disposable? = null
    private val comicApi = ComicApi(retrofit)

    fun loadLatestComic(): LiveData<RepoResult<ComicStrip>> {

        val liveData = MutableLiveData<RepoResult<ComicStrip>>()

        latestComicCall?.dispose()
        if (networkResolver.isConnected()) {
            latestComicCall = comicApi
                    .getLatest()
                    .subscribeOn(scheduler)
                    .doOnSubscribe {
                        compositeDisposable.add(it)
                    }
                    .subscribe(
                            { liveData.postValue(RepoResult(it)) },
                            { liveData.postValue(RepoResult(dataSourceError = errorMapper.convert(it))) }
                    )
        } else {
            liveData.postValue(RepoResult(dataSourceError = DataSourceError("",
                                                                            DataSourceErrorKind.NO_NETWORK)))
        }
        return liveData
    }

    fun loadComicWithId(comicId: Int): LiveData<RepoResult<ComicStrip>> {

        val liveData = MutableLiveData<RepoResult<ComicStrip>>()

        if (networkResolver.isConnected()) {
            compositeDisposable.add(
                    comicApi
                            .getForId(comicId = comicId.toString())
                            .subscribeOn(scheduler)
                            .doOnSubscribe {
                                compositeDisposable.add(it)
                            }
                            .subscribe(
                                    { liveData.postValue(RepoResult(it)) },
                                    {
                                        liveData.postValue(RepoResult(
                                                dataSourceError = errorMapper.convert(
                                                        it)))
                                    }
                            )
            )
        } else {
            liveData.postValue(RepoResult(dataSourceError = DataSourceError("",
                                                                            DataSourceErrorKind.NO_NETWORK)))
        }
        return liveData
    }

    fun clearAllJobs() {
        compositeDisposable.clear()
    }
}