package com.solucionestpvpos.themoviesegc.retrofit

import com.example.example.DetailData
import com.solucionestpvpos.themoviesegc.model.remote.CallPlaying
import com.solucionestpvpos.themoviesegc.model.remote.CallPopular
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    //TODO PLAYING
    @GET("movie/now_playing")
    fun getPlayingNow(): Call<CallPlaying>

    //TODO POPULAR
    @GET("movie/popular")
    fun getPopular(): Call<CallPopular>

    @GET("movie/{id}")
    fun getPlayingByCode(
        @Path("id") id: String): Call<DetailData>


    //TODO PLAYING
    @GET("movie/now_playing")
    suspend fun getPlayingNowCahe(): CallPlaying

}