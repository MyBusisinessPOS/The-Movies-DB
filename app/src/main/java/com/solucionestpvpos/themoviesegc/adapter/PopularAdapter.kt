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
import com.solucionestpvpos.themoviesegc.interfaces.PopularSelectListener
import com.solucionestpvpos.themoviesegc.model.ResultsPopular

class PopularAdapter(
    val context: Context,
    val mData : ArrayList<ResultsPopular>,
    val  listener: PopularSelectListener) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {


   inner class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        val textViewTitle : TextView = view.findViewById(R.id.Popular_list_Title)
        val textViewVote : TextView = view.findViewById(R.id.Popular_list_Year)
        val imageView : ImageView = view.findViewById(R.id.Popular_Item_thumbnail)

        init {
            view.setOnClickListener {
                listener.onPopularSelect(mData[adapterPosition])
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.popular_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        val playing = mData
        holder.apply {
            textViewTitle.text = playing[position].title
            textViewVote.text = playing[position].popularity.toString()
            var urlImage = "${Constant.PATH_IMAGE}${playing[position].posterPath}"
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
    override fun getItemCount(): Int = mData.size

}
