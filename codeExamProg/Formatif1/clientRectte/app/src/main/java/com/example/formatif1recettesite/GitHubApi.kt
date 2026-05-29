package com.example.formatif1recettesite

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
interface GitHubApi {
    @GET("exam/h25/{nom}")
    fun bonjour(
        @Path("nom") nom: String
    ): Call<String>

    // si post
    //     @POST("exam/h25")
    //    suspend fun bonjour(
    //        @Body requete: RequeteBonjour
    //    ): Response<String>
}