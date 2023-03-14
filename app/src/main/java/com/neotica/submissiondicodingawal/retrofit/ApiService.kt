package com.neotica.submissiondicodingawal.retrofit

import com.neotica.submissiondicodingawal.BuildConfig
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //  @Headers("Authorization: token 4c5b087d8f967fdfda3cbc967eca7ae13c33428d")
    @GET("users")
    fun getUser(): Call<List<GithubResponseItem>>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login")login: String
    ): Call<List<GithubResponseItem>>

    @GET("users")
    fun getUsername(): GithubResponseItem

 /*   @GET("/search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUser(
        @Query("q") login: String
    ): Call<List<GithubResponseItem>>*/

    companion object {
        fun loginUrl(login: String): String {
            return "users/$login/followers"
        }
    }
}