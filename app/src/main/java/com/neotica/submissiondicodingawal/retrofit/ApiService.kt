package com.neotica.submissiondicodingawal.retrofit

import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.response.SearchResponse
import com.neotica.submissiondicodingawal.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUser(@Header("Authorization") token : String): Call<List<GithubResponseItem>>

    @GET("users/{username}")
    fun getUserDetail(
        @Header("Authorization") token : String,
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Header("Authorization") token : String,
        @Path("login") login: String
    ): Call<List<GithubResponseItem>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Header("Authorization") token : String,
        @Path("login") login: String
    ): Call<List<GithubResponseItem>>

    @GET("search/users")
    fun searchUser(
        @Header("Authorization") token : String,
        @Query("q") login: String
    ): Call<SearchResponse>

    @GET("users")
    fun getUsername(@Header("Authorization") token : String): GithubResponseItem

}