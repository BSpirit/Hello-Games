package fr.epita.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GameWSInterface {

    @GET("game/list")
    fun getGames() : Call<List<Game>>

    @GET("game/details")
    fun getGameDetails(@Query("game_id") game_id : Int) : Call<Game>
}