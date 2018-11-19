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

        val retrofitClient = Retrofit.Builder()
            .baseUrl("https://androidlessonsapi.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        val service = retrofitClient.create(GameWSInterface::class.java)

        val callback : Callback<Game> = object : Callback<Game> {
            override fun onFailure(call: Call<Game>, t: Throwable) {
                Toast.makeText(this@GameDetailsActivity, "Not working: " + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if (response.code() == 200) {
                    val data : Game = response.body()!!
                    game_name.text = data.name
                    game_type.text = data.type
                    game_players.text = data.players.toString()
                    game_year.text = data.year.toString()
                    game_desc.text = data.description_en
                }
            }
        }

        val gameId = intent.getIntExtra("gameId", 0)

        service.getGameDetails(gameId).enqueue(callback)
    }
}
