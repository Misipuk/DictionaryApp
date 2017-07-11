package com.misipuk.mydictionary.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.misipuk.mydictionary.R
import com.misipuk.mydictionary.adapters.WordAdapter
import com.misipuk.mydictionary.model.WordPair
import com.misipuk.mydictionary.Config.wordsList
import java.util.*

class MainActivity : AppCompatActivity() {
    enum class Mode {
        NORMAL, REMOVE, SEARCH, SORT
    }

    var mode: Mode = Mode.NORMAL

    val searchView: MaterialSearchView  by lazy {  findViewById(R.id.search_view) as MaterialSearchView }
    val listView: ListView by lazy { findViewById(R.id.listview_words) as ListView }

    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        var wordAdapter = WordAdapter(this, wordsList)
        var listView = findViewById(R.id.listview_words) as ListView
        listView.adapter = wordAdapter
        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {

            }

            override fun onSearchViewClosed() {

                //If closed Search View , lstView will return default
                //lstView = findViewById(R.id.lstView) as ListView
                var wordAdapter = WordAdapter(this@MainActivity, wordsList)
                listView.adapter = wordAdapter

            }
        })

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText != null && newText != "") {
                    val lstFound = ArrayList<WordPair>()

                    for (item in wordsList) {
                        if (item.word.toLowerCase().contains(newText.toLowerCase())||item.trsln.toLowerCase().contains(newText.toLowerCase()))
                            lstFound.add(item)
                    }

                    var wordAdapter = WordAdapter(this@MainActivity, lstFound)
                    listView.adapter = wordAdapter
                } else {
                    //if search text is null
                    //return default
                    var wordAdapter = WordAdapter(this@MainActivity, wordsList)
                    listView.adapter = wordAdapter
                }
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        var removeMenuItem = menu.findItem(R.id.action_remove)
        var searchMenuItem = menu.findItem(R.id.action_search)
        var editMenuItem = menu.findItem(R.id.action_edit)
        var sortAbMenuItem = menu.findItem(R.id.action_sort)
        return true
    }

    override fun onBackPressed() {
        if (mode == Mode.SEARCH) {
            //searchMenuItem.collapseActionView()
        } else if (mode != Mode.NORMAL) {
           // startMode(Mode.NORMAL)
        } else {
           // finish()
        }
    }


    fun startMode(modeToStart: Mode) {
        if (mode == Mode.NORMAL){

        }
    }
}