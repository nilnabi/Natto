package com.nilnabi.android.natto.service

import android.util.Log
import com.nilnabi.android.natto.api.LiveDoorApi
import com.nilnabi.android.natto.entity.RssFeed
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.subscriber
import rx.schedulers.Schedulers

/**
 * Created by nilnabi on 2016/09/03.
 */
class RssFetchService {

    val api by lazy { LiveDoorApi().service }

    fun execute() {
        api.recentArticle()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber<RssFeed>()
                        .onNext {
                            Log.d("", it.list?.size.toString())
                        }
                        .onError {
                            Log.d("",it.message)
                        }
                )
    }

}