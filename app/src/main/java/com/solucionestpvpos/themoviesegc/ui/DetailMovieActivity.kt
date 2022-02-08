package com.solucionestpvpos.themoviesegc.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import com.example.example.DetailData
import com.google.gson.Gson
import com.solucionestpvpos.themoviesegc.R
import com.solucionestpvpos.themoviesegc.interfaces.Constant
import com.solucionestpvpos.themoviesegc.model.ResultPlaying
import com.solucionestpvpos.themoviesegc.viewmodel.PlayingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail_movie.*

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var playing: ResultPlaying
    private val playingViewModel  : PlayingViewModel by viewModels()
    private var playingDetail = DetailData()
    private var gender : String = ""
    private var languages : String = ""

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        playing = Gson().fromJson(intent.getStringExtra(Constant.PLAYING), ResultPlaying::class.java)
        playingViewModel.getPlayingByID(playing.id.toString())
        getPlayingByID()
        initData()
    }


    private fun getPlayingByID() {
        playingViewModel.getCallPlayingByID().observe(this, Observer {
            if (it == null){
                return@Observer
            }
            playingDetail = it
            Runtime_TextView.setText(""+ playingDetail.runtime +" Minutes")
            playingDetail.genres.forEach {
                gender += it.name + " "
            }
            playingDetail.spokenLanguages.forEach {
                languages += it.name + " "
            }
            Genre_TextView.setText("" + gender)
            Languaje_TextView.setText("" + languages )
        })
    }

    fun initData() {

        Title_TextView.setText("" + playing.title)
        Description_TextView.setText("" + playing.overview)
        rBar.setRating(playing.vote_average.toString().toFloat())
        ReleaseDate_TextView.setText("Year " + playing.release_date)
        Average_TextView.setText(""+playing.vote_average)
        Movie_Details_Back.setOnClickListener {
            finish()
        }

        var urlImage = "${Constant.PATH_IMAGE}${playing.backdrop_path}"
        val request = ImageRequest.Builder(this)
            .data(urlImage)
            .crossfade(true)
            .transformations(
                listOf(
                    BlurTransformation(this, 2f)
                )
            )
            .target(
                onStart = {
                    Movie_Details_Banner.setImageResource(R.drawable.loading)
                }, onSuccess = {
                    Movie_Details_Banner.setImageDrawable(it)
                    //Movie_Details_Poster.setImageDrawable(it)
                }, onError = {
                    Movie_Details_Banner.setImageResource(R.drawable.erorr)
                }

            ).build()

        this.imageLoader.enqueue(request)

        var urlImagePoster = "${Constant.PATH_IMAGE}${playing.poster_path}"
        val request2 = ImageRequest.Builder(this)
            .data(urlImagePoster)
            .crossfade(true)
            .transformations(
                listOf(
                    BlurTransformation(this, 2f)
                )
            )
            .target(
                onStart = {
                    Movie_Details_Poster.setImageResource(R.drawable.loading)

                }, onSuccess = {
                    Movie_Details_Poster.setImageDrawable(it)
                }, onError = {
                    Movie_Details_Poster.setImageResource(R.drawable.erorr)
                }

            ).build()

        this.imageLoader.enqueue(request2)
    }

    companion object {
        @JvmStatic
        fun run(context: Context, playing: ResultPlaying) {
            val intent = Intent(context, DetailMovieActivity::class.java)
            intent.putExtra(Constant.PLAYING, Gson().toJson(playing))
            context.startActivity(intent)
        }
    }
}
