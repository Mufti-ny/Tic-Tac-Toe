package com.example.tic_tac_toe

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.NumberPicker
import android.widget.Switch

class SettingsActivity : AppCompatActivity() {
    private lateinit var numberPicker: NumberPicker
    private lateinit var switchPlayer: Switch
    private lateinit var preferences: SharedPreferences

    companion object {
        const val PREFS_NAME = "TicTacToeSettings"
        const val NUMBER_OF_PLAYERS = "NumberOfPlayers"
        const val WHO_PLAYS_FIRST = "WhoPlaysFirst"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        numberPicker = findViewById<NumberPicker>(R.id.numberPicker).apply {
            minValue = 1
            maxValue = 2
            value = preferences.getInt(NUMBER_OF_PLAYERS, 2)
        }

        switchPlayer = findViewById<Switch>(R.id.switchPlayer).apply {
            isChecked = preferences.getBoolean(WHO_PLAYS_FIRST, true) // true for X, false for O
            setOnCheckedChangeListener { _, isChecked ->
                with(preferences.edit()) {
                    putBoolean(WHO_PLAYS_FIRST, isChecked)
                    apply()
                }
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

    override fun onPause() {
        super.onPause()
        // Save settings when the user leaves the settings screen
        with(preferences.edit()) {
            putInt(NUMBER_OF_PLAYERS, numberPicker.value)
            apply()
        }
    }
}