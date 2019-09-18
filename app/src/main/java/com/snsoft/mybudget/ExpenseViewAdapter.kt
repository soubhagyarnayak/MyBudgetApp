package com.snsoft.mybudget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class ExpenseViewAdapter : BaseAdapter {

    var context: Context? = null
    var expenses : List<ExpenseEntry> = ArrayList<ExpenseEntry>()

    constructor(context: Context, expenses: List<ExpenseEntry>) : super() {
        this.context = context
        this.expenses = expenses
    }

    override fun getCount(): Int {
        return expenses.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView: TextView
        if (convertView == null) {
            textView = TextView(context)
            textView.text = position.toString()
        } else {
            textView = (convertView as TextView?)!!
        }
        return textView
    }

}