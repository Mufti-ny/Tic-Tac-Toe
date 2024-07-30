package com.example.tic_tac_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class GameActivity : AppCompatActivity() {
    private var currentPlayer = "X"
    private lateinit var buttons: Array<Array<ImageButton>>
    private var roundCount = 0
    private var gameEnded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        buttons = Array(3) { r ->
            Array(3) { c ->
                initButton(r, c)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle the action bar item clicks here.
        when (item.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        val preferences = getSharedPreferences(SettingsActivity.PREFS_NAME, MODE_PRIVATE)
        val numberOfPlayers = preferences.getInt(SettingsActivity.NUMBER_OF_PLAYERS, 2)
        val whoPlaysFirst = preferences.getBoolean(SettingsActivity.WHO_PLAYS_FIRST, true)

        // Apply these settings to your game's logic
        currentPlayer = if (whoPlaysFirst) "X" else "O"

        // If numberOfPlayers is 1, implement logic for AI moves
    }

    private fun initButton(row: Int, col: Int): ImageButton {
        val btnId = "btn_${row}${col}"
        val resId = resources.getIdentifier(btnId, "id", packageName)
        val button: ImageButton = findViewById(resId) ?: throw IllegalStateException("View ID for button not found")

        button.setOnClickListener {
            if (!gameEnded) {
                if (currentPlayer == "X") {
                    button.setImageResource(R.drawable.x_drawable)
                    button.tag = "X"
                    button.isEnabled = false
                    roundCount++
                    currentPlayer = "O"
                } else {
                    button.setImageResource(R.drawable.o_drawable)
                    button.tag = "O"
                    button.isEnabled = false
                    roundCount++
                    currentPlayer = "X"

                }
                if (checkForWin()) {
                    gameEnded = true
                    val winner = if (currentPlayer == "X") "Player 1" else "Player 2"
                    val winnerPiece = if (currentPlayer == "X") "O" else "X"
                    Toast.makeText(this@GameActivity, "$winner wins!", Toast.LENGTH_SHORT).show()
                    saveGameResult(winner, winnerPiece)
                } else if (roundCount == 9) {
                    gameEnded = true
                    Toast.makeText(this@GameActivity, "Draw!", Toast.LENGTH_SHORT).show()
                    saveGameResult("Draw", "")
                }
            }
        }

        return button
    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { r ->
            Array(3) { c ->
                buttons[r][c].tag as? String // Cast to String, as tag returns an Object
            }
        }

        for (i in 0..2) {
            if ((field[i][0] == field[i][1]) && (field[i][1] == field[i][2]) && !field[i][0].isNullOrEmpty()) {
                return true
            }

            if ((field[0][i] == field[1][i]) && (field[1][i] == field[2][i]) && !field[0][i].isNullOrEmpty()) {
                return true
            }
        }

        if ((field[0][0] == field[1][1]) && (field[1][1] == field[2][2]) && !field[0][0].isNullOrEmpty()) {
            return true
        }

        if ((field[0][2] == field[1][1]) && (field[1][1] == field[2][0]) && !field[0][2].isNullOrEmpty()) {
            return true
        }

        return false
    }


    private fun saveGameResult(winner: String, piece: String) {
        val sharedPreferences = getSharedPreferences("game_history", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val historySize = sharedPreferences.getInt("history_size", 0)

        val gameData = "Game ${historySize + 1} - Winner: $winner - Piece: $piece"
        editor.putString("game_$historySize", gameData)
        editor.putInt("history_size", historySize + 1)
        editor.apply()
    }

}