package com.ideateknoloji.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okio.IOException
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_filmler.*
import kotlinx.android.synthetic.main.activity_main.filmlerRv
import kotlinx.android.synthetic.main.activity_main.toolbarFilmler


class FilmlerActivity : AppCompatActivity() {

    private lateinit var filmList: ArrayList<Filmler>
    private lateinit var adapter: FilmlerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filmler)

        // val film_id = intent.getSerializableExtra("filmId") as Filmler
        toolbarFilmler.title = "Filmler"
        setSupportActionBar(toolbarFilmler)

        filmlerRv.setHasFixedSize(true)
        filmlerRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        tumFilmler()
    }

    fun tumFilmler() {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://imdb-top-100-movies.p.rapidapi.com/")
            .addHeader("X-RapidAPI-Key", "dd6fc297e9mshea6a76f8f71eef0p15dd98jsnc1063b79c170")
            .addHeader("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com")
            .build()

        filmList = ArrayList()

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
                    val itemType = object : TypeToken<List<Filmler>>() {}.type
                    var itemList = gson.fromJson<List<Filmler>>(resStr, itemType)

                    filmList = itemList as ArrayList<Filmler>

                    runOnUiThread {
                        adapter = FilmlerAdapter(filmlerRv.context, filmList)

                        filmlerRv.adapter = adapter
                    }
                }
            }
        })

    }
}