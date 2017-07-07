package com.misipuk.mydictionary

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by Maks on 07.07.2017.
 */
object Config {
    val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()!!
}