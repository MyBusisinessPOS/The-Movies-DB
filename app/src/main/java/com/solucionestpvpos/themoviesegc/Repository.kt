package com.solucionestpvpos.themoviesegc

import com.epam.coroutinecache.api.CacheParams
import com.epam.coroutinecache.api.CoroutinesCache
import com.epam.coroutinecache.mappers.GsonMapper
import com.solucionestpvpos.themoviesegc.model.remote.CallPlaying
import com.solucionestpvpos.themoviesegc.retrofit.ApiServices
import java.io.File
import javax.inject.Inject


class Repository @Inject constructor (private val cacheDirectory: File)  {

    private val coroutinesCache = CoroutinesCache(CacheParams(10, GsonMapper(), cacheDirectory))

    private val cacheProviders: CacheProviders = coroutinesCache.using(CacheProviders::class.java)

    suspend fun getData() : CallPlaying =  cacheProviders.getDataPlaying(ApiServices::getPlayingNowCahe )

}