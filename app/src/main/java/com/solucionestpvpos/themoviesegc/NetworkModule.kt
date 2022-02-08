package com.solucionestpvpos.themoviesegc

import com.solucionestpvpos.themoviesegc.interfaces.Constant
import com.solucionestpvpos.themoviesegc.retrofit.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return  Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${Constant.API_KEY_MOVIES}")
                    .build()
                chain.proceed(request)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiservice(retrofit: Retrofit) : ApiServices{
        return retrofit.create(ApiServices::class.java)
    }

}