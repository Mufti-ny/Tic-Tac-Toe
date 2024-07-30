package com.example.tic_tac_toe

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView

class HistoryActivity : AppCompatActivity() {
    private lateinit var listView: ListView // Declare listView as a member variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        listView = findViewById(R.id.listView)


//        val listView: ListView = findViewById(R.id.listView)
        val history = getGameHistory()
        val adapter = HistoryAdapter(this, history)
        listView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getGameHistory(): List<HistoryItem> {
        val sharedPreferences = getSharedPreferences("game_history", Context.MODE_PRIVATE)
        val historySize = sharedPreferences.getInt("history_size", 0)
        val history = mutableListOf<HistoryItem>()

        for (i in 0 until historySize) {
            val gameData = sharedPreferences.getString("game_$i", null)?.split(" - ")
            if (gameData != null && gameData.size >= 3) {
                val gameNumber = gameData[0].removePrefix("Game ")
                val winner = gameData[1].removePrefix("Winner: ")
                val piece = gameData[2].removePrefix("Piece: ")
                history.add(HistoryItem(gameNumber.toInt(), winner, piece))
            }
        }

        return history
    }

    fun clearHistory(view: View) {
        val sharedPreferences = getSharedPreferences("game_history", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }

        // Update the list view
        val history = getGameHistory() // This should now be empty
        val adapter = HistoryAdapter(this, history)
        listView.adapter = adapter
    }




}