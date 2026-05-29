package com.example.formatif1

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("exam/h25/{nom}")
    suspend fun bonjour(
        @Path("nom") nom: String
    ): Response<String>

    // si post
    //     @POST("exam/h25")
    //    suspend fun bonjour(
    //        @Body requete: RequeteBonjour
    //    ): Response<String>
}