package fr.epita.hellogames

data class Game(val id : Int,
                val name : String,
                val type : String?,
                val players : Int?,
                val year : Int?,
                val url : String?,
                val description_fr : String?,
                val description_en : String?)