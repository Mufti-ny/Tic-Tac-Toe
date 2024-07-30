package com.example.tic_tac_toe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tic_tac_toe.ui.theme.TicTacToeTheme
import android.content.res.Configuration





class MainActivity : ComponentActivity() {

    private lateinit var play_game_button: Button
    private lateinit var history_button: Button
    private lateinit var settings_button: Button
    private lateinit var exit_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      setContentView(R.layout.welcome_screen)




        play_game_button = findViewById(R.id.play_game_button)
        history_button = findViewById(R.id.history_button)
        settings_button = findViewById(R.id.settings_button)
        exit_button = findViewById(R.id.exit_button)


        play_game_button.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }

        history_button.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }

        settings_button.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
        exit_button.setOnClickListener {
            finishAffinity()
        }

    }
}






@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
        Greeting("Android")
    }
}