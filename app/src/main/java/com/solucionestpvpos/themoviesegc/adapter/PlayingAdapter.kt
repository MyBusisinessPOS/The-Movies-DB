package com.solucionestpvpos.themoviesegc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import com.solucionestpvpos.themoviesegc.R
import com.solucionestpvpos.themoviesegc.interfaces.Constant
import com.solucionestpvpos.themoviesegc.interfaces.PlayingSelectListener
import com.solucionestpvpos.themoviesegc.model.ResultPlaying

class PlayingAdapter(
    val context : Context,
    val callPlayings : ArrayList<ResultPlaying>,
    val listener: PlayingSelectListener) :
    RecyclerView.Adapter<PlayingAdapter.ViewHolder>(){

    private val callResultsPlayings = ArrayList<ResultPlaying>()
    init {
        callResultsPlayings.addAll(callPlayings)
    }
   inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val textViewTitle : TextView = view.findViewById(R.id.Movie_list_Title)
        val textViewVote : TextView = view.findViewById(R.id.Movie_list_Year)
        val imageView : ImageView = view.findViewById(R.id.Movie_Item_thumbnail)

        init {
            view.setOnClickListener {
                listener.onPlayingSelect(callPlayings[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playing = callPlayings
        holder.apply {
            textViewTitle.text = playing[position].title
            textViewVote.text = playing[position].vote_average.toString()
            var urlImage = "${Constant.PATH_IMAGE}${playing[position].poster_path}"
            val request = ImageRequest.Builder(context)
                .data(urlImage)
                .crossfade(true)
                .transformations(listOf(
                    BlurTransformation(context, 2f)
                ))
                .target(
                    onStart = {
                        imageView.setImageResource(R.drawable.loading)
                    },onSuccess = {
                        imageView.setImageDrawable(it)
                    },onError = {
                        imageView.setImageResource(R.drawable.erorr)
                    }

                ).build()

            context.imageLoader.enqueue(request)
        }
    }

    override fun getItemCount(): Int = callPlayings.size

}