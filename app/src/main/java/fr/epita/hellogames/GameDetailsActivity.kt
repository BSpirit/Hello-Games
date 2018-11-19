package fr.epita.hellogames

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_game_details.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        game_name.text = intent.getStringExtra("name")
        game_type.text = intent.getStringExtra("type")
        game_players.text = intent.getStringExtra("players")
        game_year.text = intent.getStringExtra("year")
        game_desc.text = intent.getStringExtra("desc")
    }
}
