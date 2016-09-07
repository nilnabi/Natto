package com.nilnabi.android.natto.service

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.nilnabi.android.natto.api.LiveDoorApi
import rx.schedulers.Schedulers

/**
 * Created by nilnabi on 2016/09/03.
 */
class RssFetchService {

    val api by lazy { LiveDoorApi().service2 }

    fun execute() {
        api.recentArticle().subscribeOn(Schedulers.io()).map {
            val db = FirebaseDatabase.getInstance().reference
            it.list.map {
                Pair(it.getKey().hashCode().toString(), it)
            }.let {
                it.map {
                    try {
                        db.child("sites").child(it.first)
                                .updateChildren(it.second.toMapOf())
//                        LiveDoorApi().makeService(it.second.getKey()).requestOrigin()
//                                .subscribeOn(Schedulers.computation()).map {
//                            it.list.map {
//
//                            }
//                        }
                    } catch (e: Exception) {
                        Log.e("", e.message)
                    }
                }
            }
        }.subscribe()
    }

}