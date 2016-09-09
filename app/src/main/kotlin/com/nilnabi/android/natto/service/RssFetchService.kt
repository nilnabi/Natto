package com.nilnabi.android.natto.service

import com.google.firebase.database.FirebaseDatabase
import com.nilnabi.android.natto.api.LiveDoorApi
import com.nilnabi.android.natto.extension.toMD5
import rx.schedulers.Schedulers

/**
 * Created by nilnabi on 2016/09/03.
 */
class RssFetchService {

    val api by lazy { LiveDoorApi().service2 }

    fun execute() {
        val db = FirebaseDatabase.getInstance().reference
        val observable = api.recentArticle().subscribeOn(Schedulers.io())

        observable.map { fetchData ->
            fetchData.list.map { item ->
                Pair(item.rfdUrl.toMD5(), item)
            }
        }.map {
            it.map {
                db.child("sites").child(it.first).updateChildren(it.second.toMapOf())
            }
            it
        }.map {
            it.map {
                LiveDoorApi().makeService(it.second.rfdUrl).requestOrigin()
                        .subscribeOn(Schedulers.io()).map {
                    it.list.map {
                        db.child("articles").child(it.link.toMD5()).updateChildren(it.toMapOf())
                    }
                }.subscribe()
            }
        }.subscribe()


//        observable.map { data ->
//            val result = mutableMapOf<String,Any>()
//            db.runTransaction(object: Transaction.Handler {
//                override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {
//                }
//
//                override fun doTransaction(mutableData: MutableData?): Transaction.Result {
//                    data.list.map { data ->
//                        result.put(it.rfdUrl.toMD5(), it)
//                        mutableData?.child("sites")?.child(data.rfdUrl.toMD5())?.let {
////                            it.children = data.toMapOf()
//                        }
//                    }
//                    return Transaction.success(mutableData)
//                }
//            })
//        }
//
//        api.recentArticle().subscribeOn(Schedulers.io()).map { data ->
//            data.list.map {
//                Pair(it.rfdUrl.toMD5(), it)
//            }.let {
//                it.map {
//                    try {
//                        db.child("sites").child(it.first)
//                                .updateChildren(it.second.toMapOf())
//                    } catch (e: Exception) {
//                        Log.e("", e.message)
//                    }
//                }
//            }
//        }.subscribe(subscriber<List<Any>>()
//                .onNext {
//it
//                }
//                .onError {  }
//        )
    }

}