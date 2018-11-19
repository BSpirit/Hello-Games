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
import kotlin.random.Random

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
                    val data : List<Game> = response.body()!!

                    val game1 = data.random()
                    game1_btn.text = game1.name
                    game1_btn.setOnClickListener(GameBtnListener(game1.id))

                    val game2 = data.random()
                    game2_btn.text = game2.name
                    game2_btn.setOnClickListener(GameBtnListener(game2.id))

                    val game3 = data.random()
                    game3_btn.text = game3.name
                    game3_btn.setOnClickListener(GameBtnListener(game3.id))

                    val game4 = data.random()
                    game4_btn.text = game4.name
                    game4_btn.setOnClickListener(GameBtnListener(game4.id))
                }
            }
        }

        service.getGames().enqueue(callback)
    }


    inner class GameBtnListener(val gameId : Int) : View.OnClickListener {

        override fun onClick(v: View?) {
            val explicitIntent = Intent(this@MainActivity, GameDetailsActivity::class.java)
            explicitIntent.putExtra("gameId", gameId)
            startActivity(explicitIntent)
        }
    }
}
