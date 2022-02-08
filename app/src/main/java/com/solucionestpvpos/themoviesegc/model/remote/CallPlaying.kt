package com.solucionestpvpos.themoviesegc.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.solucionestpvpos.themoviesegc.model.ResultPlaying

class CallPlaying {

    @SerializedName("page"         ) var page          : Int? = null
    @SerializedName("results"      ) var callPlaying   : List<ResultPlaying>? = null
    @SerializedName("total_pages"  ) var total_pages   : Int? = null
    @SerializedName("total_results") var total_results : Int? = null
}