package com.ideateknoloji.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_detay.*
import okhttp3.*
import okio.IOException

class DetayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detay)

        val film_id = intent.getSerializableExtra("id") as String

        getDetail(film_id)
    }

    fun getDetail(id: String) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://imdb-top-100-movies.p.rapidapi.com/${id}")
            .addHeader("X-RapidAPI-Key", "dd6fc297e9mshea6a76f8f71eef0p15dd98jsnc1063b79c170")
            .addHeader("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    val resStr = response.body!!.string()

                    val gson = Gson()
                    val itemType = object : TypeToken<Filmler>() {}.type
                    var film = gson.fromJson<Filmler>(resStr, itemType)

                    runOnUiThread {
                        textViewFilmAdi.text = film.title
                        textViewFilmRating.text = "Rating: ${film.rating}/10 IMDb"
                        textViewFilmYear.text = "${film.year}"
                        textViewRank.text = "${film.rank}"
                        textViewDescription.text = film.description
                        textViewDirectorWriter.text = film.director.joinToString("\n")
                        textViewGenres.text = film.genre.joinToString(" - ")
                    }
                }
            }
        })
    }

}