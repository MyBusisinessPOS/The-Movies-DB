package com.solucionestpvpos.themoviesegc.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.example.DetailData
import com.solucionestpvpos.themoviesegc.interfaces.Constant
import com.solucionestpvpos.themoviesegc.model.ResultsPopular
import com.solucionestpvpos.themoviesegc.model.remote.CallPopular
import com.solucionestpvpos.themoviesegc.retrofit.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor (val apiServices: ApiServices ) : ViewModel() {

    private val callPopular = MutableLiveData<List<ResultsPopular>>()
    private var popularDetail = MutableLiveData<DetailData>()

    fun callPopular(): LiveData<List<ResultsPopular>> {
        return callPopular
    }


    fun getCallPlayingByID(): MutableLiveData<DetailData>{
        return popularDetail
    }

    fun getPlayingByID(id : String){
        apiServices.getPlayingByCode(id).enqueue(object : Callback<DetailData>{
            override fun onResponse(call: Call<DetailData>, response: Response<DetailData>) {
                if (!response.isSuccessful){
                    return
                }

                val remoteCallPlayingByID = response.body()
                Log.d(
                    Constant.CALL_POPULAR_LOGS,
                    "getPlatingByID:onResponse -> ${remoteCallPlayingByID}"
                )

                if (remoteCallPlayingByID != null){
                    var playingId = remoteCallPlayingByID
                    popularDetail.postValue(playingId)
                }else{
                    popularDetail.postValue(null)
                }
            }

            override fun onFailure(call: Call<DetailData>, t: Throwable) {
                Log.d(Constant.CALL_POPULAR_LOGS, "getPlayingById:onFailur")
                popularDetail.postValue(null)
            }

        })
    }

    fun getCallPopular() {

        apiServices.getPopular().enqueue(object : Callback<CallPopular> {
            override fun onResponse(call: Call<CallPopular>, response: Response<CallPopular>) {
                if (!response.isSuccessful) {
                    Log.d(
                        Constant.CALL_POPULAR_LOGS,
                        "getCallPopulars:onResponse -> Not Successfuls"
                    )
                    callPopular.postValue(null)
                    return
                }

                val remoteCallPopular = response.body()

                Log.d(
                    Constant.CALL_POPULAR_LOGS,
                    "getCallPopulars:onResponse -> ${remoteCallPopular?.results}"
                )

                if (remoteCallPopular?.results != null) {
                    val popular = remoteCallPopular.results
                    callPopular.postValue(popular)
                } else {
                    callPopular.postValue(null)
                }
            }

            override fun onFailure(call: Call<CallPopular>, t: Throwable) {
                Log.d(Constant.CALL_POPULAR_LOGS, "getCallPopular:onFailur")
                callPopular.postValue(null)
            }

        })
    }


}