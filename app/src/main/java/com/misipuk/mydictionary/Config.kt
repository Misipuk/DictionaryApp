package com.misipuk.mydictionary

import com.misipuk.mydictionary.model.WordPair
import okhttp3.OkHttpClient
import java.util.*
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
    val wordsList: MutableList<WordPair> by lazy{
        ArrayList<WordPair>().apply {
            this.add(WordPair("Макс","Max"))
            this.add(WordPair("Димка","Dimka"))
            this.add(WordPair("Егорка","Egorka"))
            this.add(WordPair("Никитос","Nik"))
            this.add(WordPair("Руся","Ruslan"))
        }
    }
}