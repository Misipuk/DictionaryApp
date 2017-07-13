package com.misipuk.mydictionary.adapters
import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.misipuk.mydictionary.Config
import com.misipuk.mydictionary.R
import com.misipuk.mydictionary.activity.MainActivity.Mode
import com.misipuk.mydictionary.model.WordPair

/**
 * Created by Maks on 12.07.2017.
 */

class WordPairAdapter(context: Context, items:MutableList<WordPair>, mode:Mode) : CustomAdapter<WordPair>(context,items, mode){

    override val layout: Int = R.layout.item_word

    override fun View.holder() = apply {
        tag = ViewHolder((findViewById(R.id.text_word) as TextView),
                (findViewById(R.id.text_trsln) as TextView),
                (findViewById(R.id.del_button) as ImageButton))
    }

    override fun View.data(item: WordPair, position:Int) = apply {
        (tag as ViewHolder).apply {
            textWord.setText(item.word)
            textTrsln.setText(item.trsln)

            if (mode == Mode.EDIT){
                delButton.setVisibility(View.VISIBLE)
                delButton.setOnClickListener {
                    Config.wordsList.removeAt(position)
                    this@WordPairAdapter.notifyDataSetChanged()

                }
            }else{
                delButton.setVisibility(View.GONE)
            }
        }
    }

    data class ViewHolder(val textWord: TextView,
                          val textTrsln: TextView,
                          val delButton: ImageButton)
}