package com.misipuk.mydictionary.adapters


import android.app.DownloadManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.misipuk.mydictionary.Config.client
import com.misipuk.mydictionary.Config.wordsList
import com.misipuk.mydictionary.R
import com.misipuk.mydictionary.activity.MainActivity.Mode
import com.misipuk.mydictionary.model.WordPair
import okhttp3.Request
import java.util.*

/**
 * Created by Maks on 07.07.2017.
 */
class WordAdapter(var context:Context, val items: MutableList<WordPair> = ArrayList(), var mode: Mode): BaseAdapter() {

    override fun getItem(position: Int): WordPair = items[position]

    override fun getItemId(position: Int): Long=0

    override fun getCount(): Int = items.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View{
        val linflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = linflater.inflate(R.layout.item_word, parent, false)
        val textWord = view.findViewById(R.id.text_word) as TextView
        val textTrsln = view.findViewById(R.id.text_trsln) as TextView
        val delButton = view.findViewById(R.id.del_button) as ImageButton
        var wordPair = items[position]
        textWord.setText(wordPair.word)
        textTrsln.setText(wordPair.trsln)

        if (mode == Mode.EDIT){
            delButton.setVisibility(View.VISIBLE)
            delButton.setOnClickListener {
                wordsList.removeAt(position)
                this.notifyDataSetChanged()

            }
        }else{
            delButton.setVisibility(View.GONE)
        }

        return view
    }

}