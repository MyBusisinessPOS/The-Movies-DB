package com.solucionestpvpos.themoviesegc

import com.epam.coroutinecache.annotations.*
import com.solucionestpvpos.themoviesegc.model.remote.CallPlaying
import com.solucionestpvpos.themoviesegc.retrofit.ApiServices
import java.util.concurrent.TimeUnit
import kotlin.reflect.KSuspendFunction1

interface CacheProviders {
    @ProviderKey("Playing", EntryClass(CallPlaying::class))
    @LifeTime(value = 1L, unit = TimeUnit.DAYS)
    @Expirable
    @UseIfExpired
    suspend fun getDataPlaying(dataProvider: KSuspendFunction1<ApiServices, CallPlaying>) : CallPlaying


}