package com.example.formatif2

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("examen/{x}/{y}")
    fun nombresPairs(
        @Path("x") x: Int,
        @Path("y") y: Int
    ): Call<List<Int>>

    // si post
    //     @POST("exam/h25")
    //    suspend fun bonjour(
    //        @Body requete: RequeteBonjour
    //    ): Response<String>
}