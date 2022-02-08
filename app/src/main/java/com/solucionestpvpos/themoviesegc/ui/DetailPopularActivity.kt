package com.solucionestpvpos.themoviesegc.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import com.example.example.DetailData
import com.google.gson.Gson
import com.solucionestpvpos.themoviesegc.R
import com.solucionestpvpos.themoviesegc.interfaces.Constant
import com.solucionestpvpos.themoviesegc.model.ResultsPopular
import com.solucionestpvpos.themoviesegc.viewmodel.PopularViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.activity_detail_popular.*

@AndroidEntryPoint
class DetailPopularActivity : AppCompatActivity() {

    private lateinit var popular: ResultsPopular
    private val popularViewModel: PopularViewModel by viewModels()
    private var popularDetail = DetailData()
    private var gender: String = ""
    private var languages: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_popular)

        popular =
            Gson().fromJson(intent.getStringExtra(Constant.POPULAR), ResultsPopular::class.java)
        popularViewModel.getPlayingByID(popular.id.toString())
        getPopularByID()
        initData()
    }

    private fun initData() {
        Popular_Title_TextView.setText("" + popular.title)
        Popular_Description_TextView.setText("" + popular.overview)
        rBarPopular.setRating(popular.voteAverage.toString().toFloat())
        Popular_ReleaseDate_TextView.setText("Year " + popular.releaseDate)

        Popular_Details_Back.setOnClickListener {
            finish()
        }

        var urlImage = "${Constant.PATH_IMAGE}${popular.backdropPath}"
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
                    Popular_Details_Banner.setImageResource(R.drawable.loading)
                }, onSuccess = {
                    Popular_Details_Banner.setImageDrawable(it)
                    //Movie_Details_Poster.setImageDrawable(it)
                }, onError = {
                    Popular_Details_Banner.setImageResource(R.drawable.erorr)
                }

            ).build()

        this.imageLoader.enqueue(request)

        var urlImagePoster = "${Constant.PATH_IMAGE}${popular.posterPath}"
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
                    Popular_Details_Poster.setImageResource(R.drawable.loading)
                }, onSuccess = {
                    Popular_Details_Poster.setImageDrawable(it)
                }, onError = {
                    Popular_Details_Poster.setImageResource(R.drawable.erorr)
                }

            ).build()

        this.imageLoader.enqueue(request2)
    }

    private fun getPopularByID() {
        popularViewModel.getCallPlayingByID().observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            popularDetail = it
            Popular_Runtime_TextView.setText("" + popularDetail.runtime + " Minutes")
            popularDetail.genres.forEach {
                gender += it.name + " "
            }
            popularDetail.spokenLanguages.forEach {
                languages += it.name + " "
            }
            Popular_Genre_TextView.setText("" + gender)
            Popular_Languaje_TextView.setText("" + languages)
            Popular_Average_TextView.setText("" + popularDetail.voteAverage)
        })
    }

    companion object {
        @JvmStatic
        fun run(context: Context, popular: ResultsPopular) {
            val intent = Intent(context, DetailPopularActivity::class.java)
            intent.putExtra(Constant.POPULAR, Gson().toJson(popular))
            context.startActivity(intent)
        }
    }

}