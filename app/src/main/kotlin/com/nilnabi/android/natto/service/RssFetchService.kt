package com.nilnabi.android.natto.service

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.nilnabi.android.natto.api.LiveDoorApi
import rx.schedulers.Schedulers

/**
 * Created by nilnabi on 2016/09/03.
 */
class RssFetchService {

    val api by lazy { LiveDoorApi().service }

    fun execute() {
        api.recentArticle().subscribeOn(Schedulers.newThread()).map {
            val db = FirebaseDatabase.getInstance().reference
            it.list.map {
                Pair(it.getKey().hashCode().toString(), it)
            }.let {
                it.map {
                    try {
                        db.child("sites").child(it.first)
                                .updateChildren(it.second.toMapOf())
                    } catch (e: Exception) {
                        Log.e("", e.message)
                    }
                }
            }
//            data.map {
//                db.child("sites").updateChildren(it)
//            }
//            db.child("sites").updateChildren(data.map {  })
            println()
//            val siteLists = it.list.map {
//                it.link.replace(Regex("archives/.+"), "index.rdf")
//            }.distinct()
//                db.runTransaction(object :Transaction.Handler {
//                    override fun onComplete(databaseError: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {
//                        databaseError?.let {
//                            Log.d("", databaseError.message)
//                        }
//                    }
//
//                    override fun doTransaction(mutableData: MutableData?): Transaction.Result {
//                        mutableData?.let {
//                            it.child("sites").value = site
//                        }
//                        return Transaction.success(mutableData)
//                    }
//
//                })
//                db.child("sites").push().setValue(it)

//            db.child("sites").push().setValue(siteLists)
//            siteLists.forEach { db.child("sites").updateChildren(mapOf(it) }
        }.subscribe()
    }

}