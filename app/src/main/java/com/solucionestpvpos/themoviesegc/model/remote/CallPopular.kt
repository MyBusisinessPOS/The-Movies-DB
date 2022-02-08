package com.solucionestpvpos.themoviesegc.model.remote

import com.google.gson.annotations.SerializedName
import com.solucionestpvpos.themoviesegc.model.ResultsPopular

class CallPopular {
    @SerializedName("page"          ) var page         : Int?               = null
    @SerializedName("results"       ) var results      : ArrayList<ResultsPopular> = arrayListOf()
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null
    @SerializedName("total_results" ) var totalResults : Int?               = null
}