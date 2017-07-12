package com.misipuk.mydictionary.activity

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.misipuk.mydictionary.R
import com.misipuk.mydictionary.adapters.WordAdapter
import com.misipuk.mydictionary.model.WordPair
import com.misipuk.mydictionary.Config.wordsList
import java.io.ByteArrayOutputStream
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    enum class Mode {
        NORMAL, EDIT, SEARCH
    }

    var mode: Mode = Mode.NORMAL

    //val searchView: MaterialSearchView  by lazy {  findViewById(R.id.search_view) as MaterialSearchView }
    val listView: ListView by lazy { findViewById(R.id.listview_words) as ListView }
    lateinit var mSearchView : SearchView

    lateinit var addMenuItem: MenuItem
    lateinit var editMenuItem : MenuItem
    lateinit var sortAbMenuItem: MenuItem
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
        addMenuItem = menu.findItem(R.id.action_add)
        editMenuItem = menu.findItem(R.id.action_edit)
        sortAbMenuItem = menu.findItem(R.id.action_sort)
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, this)
        startMode(Mode.NORMAL)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item){
            editMenuItem ->{
                startMode(Mode.EDIT)
            }
            addMenuItem ->{
                var mBuilder = AlertDialog.Builder(this@MainActivity)
                var mView = layoutInflater.inflate(R.layout.add_dialog,null)
                var wordText: EditText = mView.findViewById(R.id.d_word) as EditText
                var trslnText: EditText = mView.findViewById(R.id.d_trsln) as EditText
                var addButton: Button = mView.findViewById(R.id.btnAdd) as Button
                mBuilder.setView(mView);
                var dialog: AlertDialog = mBuilder.create();

                dialog.show();
                addButton.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View) {
                        if(!wordText.getText().toString().isEmpty() && !trslnText.getText().toString().isEmpty()) {
                            wordsList.add(WordPair(wordText.getText().toString(),trslnText.getText().toString()))
                            var wordAdapter = WordAdapter(this@MainActivity, wordsList)
                            listView.adapter = wordAdapter
                            dialog.dismiss();
                        }
                    }
                })

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun startMode(modeToStart: Mode) {
        when (modeToStart){
            Mode.NORMAL -> {
                addMenuItem.isVisible = false
                //microMenuItem.setVisible(false)
                sortAbMenuItem.isVisible = false
                searchMenuItem.setVisible(true)
                editMenuItem.isVisible = true
            }
            Mode.SEARCH ->{
                addMenuItem!!.isVisible = false
                searchMenuItem!!.isVisible = false
                sortAbMenuItem.isVisible = false
                addMenuItem.isVisible = false
            }
            Mode.EDIT ->{
                addMenuItem.isVisible = true
                sortAbMenuItem.isVisible = true
                searchMenuItem!!.isVisible = true
                editMenuItem.isVisible = false
            }
        }
        mode = modeToStart
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        //SEARCHVIEW ACTIVITY
        startMode(Mode.SEARCH)
        //CLOSE DRAWERLAYOUT
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        //SEARCHVIEW CLOSE
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