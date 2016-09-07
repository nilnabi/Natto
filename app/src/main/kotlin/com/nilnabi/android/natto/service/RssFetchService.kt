package com.nilnabi.android.natto.service

import com.nilnabi.android.natto.api.LiveDoorApi
import rx.schedulers.Schedulers

/**
 * Created by nilnabi on 2016/09/03.
 */
class RssFetchService {

    val api by lazy { LiveDoorApi().service }

    fun execute() {
//        api.recentArticle()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber<RssFeed>()
//                        .onNext {
//                            Log.d("", it.list?.size.toString())
//
//                        }
//                        .onError {
//                            Log.d("",it.message)
//                        }
//                )

        api.recentArticle().subscribeOn(Schedulers.io()).map {
            val rdfList = mutableListOf<String>()
            it.list.filter {
                it.link.replace(Regex("archives/.+"), "index.rdf").let { rdf ->
                    rdfList.find { it.equals(rdf) } ?: rdfList.add(rdf)
                }
                true
            }.let {
                println(rdfList.size)
            }

        }.subscribe()


    }

}