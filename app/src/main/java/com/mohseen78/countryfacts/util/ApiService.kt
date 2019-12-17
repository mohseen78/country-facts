package com.mohseen78.countryfacts.util

import com.mohseen78.countryfacts.model.FactsList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Retrofit builder to build a retrofit object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// interface to get [getFactsProperties]
interface ApiService {
    @GET("facts.json")
    fun getProperties(): Call<FactsList>
}

// public Api object that exposes the lazy-initialized Retrofit service
object FactsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(
            ApiService::class.java
        )
    }
}