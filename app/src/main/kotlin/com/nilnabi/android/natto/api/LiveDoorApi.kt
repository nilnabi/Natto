package com.nilnabi.android.natto.api

import com.nilnabi.android.natto.entity.RssFeed
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import rx.Observable

/**
 * Created by nilnabi on 2016/09/03.
 */
class LiveDoorApi {

    val service: Service =
        Retrofit.Builder()
                .baseUrl("http://blog.livedoor.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
        .create(Service::class.java)

    val service2: Service = makeService("http://blog.livedoor.com/")

    fun makeService(url: String): Service {
        return Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(Service::class.java)
    }

    interface Service {

        @GET("/xml/recent_articles.rdf")
        fun recentArticle(): Observable<RssFeed>

        @GET
        fun requestOrigin(): Observable<RssFeed>
    }

}
