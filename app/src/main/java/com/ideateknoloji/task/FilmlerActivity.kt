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

/*
        val f1 = Filmler(1, "Deneme", 2008, "test", "de")
        val f2 = Filmler(1, "Deneme22", 2028, "test", "de")
        val f3 = Filmler(1, "Deneme33", 2018, "test", "de")

        filmList.add(f1)
        filmList.add(f2)
        filmList.add(f3)
*/

        tumFilmler()
    }

    fun tumFilmler() {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://imdb-top-100-movies.p.rapidapi.com/")
            .addHeader("X-RapidAPI-Key", "3685b9a11amsh5acc32d66b6a7c0p1910fbjsnd55e43062df9")
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


                    // response body parse


                    println("gelen body")
                    //println("${response.body!!.string()::class.simpleName}")    // "Int"

                    val resStr = response.body!!.string()
                    println("gelen body2222 ${resStr}")

                    val gson = Gson()
                    val itemType = object : TypeToken<List<Filmler>>() {}.type
                    var itemList = gson.fromJson<List<Filmler>>(resStr, itemType)

                    println("gelen 111111 ${itemList}")

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