package com.ideateknoloji.task

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip

class FilmlerAdapter (private val mContext: Context, private val filmlerList:List<Filmler>)
    : RecyclerView.Adapter<FilmlerAdapter.CardTasarimTutucu>(){
    inner class CardTasarimTutucu(tasarim: View) : RecyclerView.ViewHolder(tasarim){
        var film_card: CardView
        var textViewTitle : TextView
        var textViewRating: TextView
        var chipYear: Chip

        init {
            film_card = tasarim.findViewById(R.id.film_card)
            textViewTitle = tasarim.findViewById(R.id.textViewTitle)
            textViewRating = tasarim.findViewById(R.id.textViewRating)
            chipYear = tasarim.findViewById(R.id.chipYear)

        }
    }

    override  fun onCreateViewHolder (parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.film_card_tasarim, parent, false)
        return CardTasarimTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return filmlerList.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val film = filmlerList.get(position)
        holder.textViewTitle.text = film.title
        holder.textViewRating.text = "Rating : ${film.rating}";
        holder.chipYear.text = "${film.year}";

        holder.film_card.setOnClickListener{
            val intent = Intent(mContext, DetayActivity::class.java)
            intent.putExtra("id", film.id)
            mContext.startActivity(intent)
        }
    }
}