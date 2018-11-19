package fr.epita.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitClient = Retrofit.Builder()
            .baseUrl("https://androidlessonsapi.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        val service = retrofitClient.create(GameWSInterface::class.java)

        val callback : Callback<List<Game>> = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Not working: " + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.code() == 200) {

                    val games = response.body()!!.shuffled().take(4)

                    game1_btn.text = games[0].name
                    game1_btn.setOnClickListener(GameBtnListener(games[0].id))

                    game2_btn.text = games[1].name
                    game2_btn.setOnClickListener(GameBtnListener(games[1].id))

                    game3_btn.text = games[2].name
                    game3_btn.setOnClickListener(GameBtnListener(games[2].id))

                    game4_btn.text = games[3].name
                    game4_btn.setOnClickListener(GameBtnListener(games[3].id))
                }
            }
        }

        service.getGames().enqueue(callback)
    }


    inner class GameBtnListener(val gameId : Int) : View.OnClickListener {

        override fun onClick(v: View?) {

            Toast.makeText(this@MainActivity, "Loading data...", Toast.LENGTH_SHORT).show()

            val retrofitClient = Retrofit.Builder()
                .baseUrl("https://androidlessonsapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

            val service = retrofitClient.create(GameWSInterface::class.java)

            val callback : Callback<Game> = object : Callback<Game> {
                override fun onFailure(call: Call<Game>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Not working: " + t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Game>, response: Response<Game>) {
                    if (response.code() == 200) {

                        val game = response.body()!!

                        val explicitIntent = Intent(this@MainActivity, GameDetailsActivity::class.java)
                        explicitIntent.putExtra("name", game.name)
                        explicitIntent.putExtra("type", game.type)
                        explicitIntent.putExtra("players", game.players.toString())
                        explicitIntent.putExtra("year", game.year.toString())
                        explicitIntent.putExtra("desc", game.description_en)
                        startActivity(explicitIntent)
                    }
                }
            }


            service.getGameDetails(gameId).enqueue(callback)
        }
    }
}
