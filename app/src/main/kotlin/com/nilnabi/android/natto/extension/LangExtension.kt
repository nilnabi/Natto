package com.nilnabi.android.natto.extension

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Created by y.ebina on 2016/09/08.
 */

fun String.toMD5(): String {
    return BigInteger(1, MessageDigest.getInstance("MD5")
            .digest(this.toByteArray()))
            .toString(16)
}