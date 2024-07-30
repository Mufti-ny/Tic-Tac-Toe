package com.example.tic_tac_toe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HistoryAdapter(context: Context, private val historyList: List<HistoryItem>)
    : ArrayAdapter<HistoryItem>(context, 0, historyList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_history, parent, false)
        }

        val currentHistoryItem = historyList[position]

        listItemView!!.findViewById<TextView>(R.id.textViewGameNumber).text = currentHistoryItem.gameNumber.toString()
        listItemView.findViewById<TextView>(R.id.textViewWinner).text = currentHistoryItem.winner
        listItemView.findViewById<TextView>(R.id.textViewPiece).text = currentHistoryItem.piece

        return listItemView


    }


}
