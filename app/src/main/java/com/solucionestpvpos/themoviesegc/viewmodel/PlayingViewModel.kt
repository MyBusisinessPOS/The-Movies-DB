package com.solucionestpvpos.themoviesegc.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.example.DetailData
import com.solucionestpvpos.themoviesegc.interfaces.Constant
import com.solucionestpvpos.themoviesegc.model.ResultPlaying
import com.solucionestpvpos.themoviesegc.model.remote.CallPlaying
import com.solucionestpvpos.themoviesegc.retrofit.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PlayingViewModel @Inject constructor (val apiServices: ApiServices) : ViewModel() {

    private val callPlaying = MutableLiveData<List<ResultPlaying>>()
    private var playingDetail = MutableLiveData<DetailData>()

    fun callPlaying():LiveData<List<ResultPlaying>>{
        return callPlaying
    }

    fun getCallPlayingByID(): MutableLiveData<DetailData>{
        return playingDetail
    }

    fun  getPlayingByID(id : String){
        apiServices.getPlayingByCode(id).enqueue(object : Callback<DetailData>{
            override fun onResponse(call: Call<DetailData>, response: Response<DetailData>) {
                if (!response.isSuccessful){
                 return
                }

                val remoteCallPlayingByID = response.body()
                Log.d(
                    Constant.CALL_PLAYING_LOGS,
                    "getPlatingByID:onResponse -> ${remoteCallPlayingByID}"
                )

                if (remoteCallPlayingByID != null){
                    var playingId = remoteCallPlayingByID
                    playingDetail.postValue(playingId)
                }else{
                    playingDetail.postValue(null)
                }
            }

            override fun onFailure(call: Call<DetailData>, t: Throwable) {
                Log.d(Constant.CALL_PLAYING_LOGS, "getPlayingById:onFailur")
                playingDetail.postValue(null)
            }

        })
    }

    fun getCallPlayings(){

        apiServices.getPlayingNow().enqueue(object : Callback<CallPlaying>{
            override fun onResponse(call: Call<CallPlaying>, response: Response<CallPlaying>) {
                if (!response.isSuccessful){
                    Log.d(Constant.CALL_PLAYING_LOGS, "getCallPlaying:onResponse -> Not Successfuls")
                    callPlaying.postValue(null)
                    return
                }

                val remoteCallPlayings = response.body()

                Log.d(
                    Constant.CALL_PLAYING_LOGS,
                    "getCallPlaying:onResponse -> ${remoteCallPlayings?.callPlaying}"
                )

                if (remoteCallPlayings?.callPlaying != null){
                    val playing = remoteCallPlayings.callPlaying
                    callPlaying.postValue(playing)
                }else{
                    callPlaying.postValue(null)
                }
            }

            override fun onFailure(call: Call<CallPlaying>, t: Throwable) {
                Log.d(Constant.CALL_PLAYING_LOGS, "getCallPlaying:onFailur")
                callPlaying.postValue(null)
            }
        })
    }


}