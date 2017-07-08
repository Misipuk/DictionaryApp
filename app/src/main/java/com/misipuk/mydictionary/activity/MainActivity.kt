package com.misipuk.mydictionary.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.misipuk.mydictionary.R
import com.misipuk.mydictionary.adapters.WordAdapter
import com.misipuk.mydictionary.model.WordPair
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var items: MutableList<WordPair> = ArrayList()
        items.add(WordPair("Max","Макс"))
        items.add(WordPair("Dimka","Димка"))
        items.add(WordPair("Egorka","Егорка"))
        items.add(WordPair("Nik","Никитос"))
        items.add(WordPair("Ruslan","Руся"))
        var wordAdapter = WordAdapter(this, items)
        var listView = findViewById(R.id.listview_words) as ListView
        listView.adapter = wordAdapter

    }
}
