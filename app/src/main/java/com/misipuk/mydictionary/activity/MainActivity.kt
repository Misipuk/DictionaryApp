package com.misipuk.mydictionary.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.misipuk.mydictionary.R
import com.misipuk.mydictionary.adapters.WordAdapter
import com.misipuk.mydictionary.model.WordPair
import com.misipuk.mydictionary.Config.wordsList
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    enum class Mode {
        NORMAL, REMOVE, SEARCH, SORT
    }

    var mode: Mode = Mode.NORMAL

    //val searchView: MaterialSearchView  by lazy {  findViewById(R.id.search_view) as MaterialSearchView }
    val listView: ListView by lazy { findViewById(R.id.listview_words) as ListView }
    lateinit var removeMenuItem: MenuItem
    lateinit var editMenuItem : MenuItem
    lateinit var sortAbMenuItem: MenuItem
    lateinit var mSearchView : SearchView
    lateinit var searchMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        var wordAdapter = WordAdapter(this, wordsList)
        var listView = findViewById(R.id.listview_words) as ListView
        listView.adapter = wordAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        searchMenuItem = menu.findItem(R.id.action_search)
        mSearchView = searchMenuItem.actionView as SearchView
        mSearchView.setOnQueryTextListener(this)
        removeMenuItem = menu.findItem(R.id.action_remove)
        editMenuItem = menu.findItem(R.id.action_edit)
        sortAbMenuItem = menu.findItem(R.id.action_sort)
        startMode(Mode.NORMAL)
        return true
    }

    fun startMode(modeToStart: Mode) {
        when (modeToStart){
            Mode.NORMAL -> {
                removeMenuItem.isVisible = false
                //microMenuItem.setVisible(false)
                sortAbMenuItem.isVisible = false
                searchMenuItem.setVisible(true)
                editMenuItem.isVisible = true
            }
            Mode.SEARCH ->{
                removeMenuItem!!.isVisible = false
                searchMenuItem!!.isVisible = false
            }
        }
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        startMode(Mode.SEARCH)
        //CLOSE DRAWERLAYOUT
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        startMode(Mode.NORMAL)
        var wordAdapter = WordAdapter(this@MainActivity, wordsList)
        listView.adapter = wordAdapter
        return true
    }

    override fun onBackPressed() {
        if (mode == Mode.SEARCH) {
            searchMenuItem.collapseActionView()
        } else if (mode != Mode.NORMAL) {
            startMode(Mode.NORMAL)
        } else {
            finish()
        }
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        //searchAutoCompleterAdapter!!.filter.filter(queryText)
        if (newText != null && newText != "") {
            var lstFound = ArrayList<WordPair>()

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
        return false
    }
}