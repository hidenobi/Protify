package com.proptit.protify.services

import com.proptit.protify.models.Data
import com.proptit.protify.models.LoginResponse

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("https://deezerdevs-deezer.p.rapidapi.com/search")
    fun getListMusic(
        @Query("q") q: String,
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") apiHost: String
    ): Call<Data>
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email: String, @Field("password") password: String): Call<LoginResponse>
}