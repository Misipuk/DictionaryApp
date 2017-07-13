package com.misipuk.mydictionary.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.content.Context
import android.view.LayoutInflater
import com.misipuk.mydictionary.activity.MainActivity.Mode
import java.util.*

/**
 * Created by Maks on 12.07.2017.
 */

abstract class CustomAdapter<T>(var context:Context, open val items: MutableList<T> = ArrayList(), var mode: Mode) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(i: Int): T = items[i]

    fun add(list: Collection<T>) = apply {
        items.addAll(list).let { notifyDataSetChanged() }
    }

    fun clear(list: Collection<T> = ArrayList()) = apply {
        items.clear()
        add(list)
        notifyDataSetChanged()
    }

    override fun getItemId(i: Int): Long = 0

    @SuppressLint("ViewHolder") //it shouldn't be warned?
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View{
        val lInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return lInflater.inflate(layout, parent, false).holder().data(items[position], position)
    }


    abstract fun View.holder(): View

    abstract fun View.data(item: T, position:Int): View

    abstract val layout: Int
}
