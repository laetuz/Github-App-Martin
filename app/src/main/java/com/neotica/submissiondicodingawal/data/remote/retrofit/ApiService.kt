package com.neotica.submissiondicodingawal.data.remote.retrofit

import com.neotica.submissiondicodingawal.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.data.remote.model.SearchResponse
import com.neotica.submissiondicodingawal.data.remote.model.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUser(): Call<List<GithubResponseItem>>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<GithubResponseItem>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<GithubResponseItem>>

    @GET("search/users")
    fun searchUser(
        @Query("q") login: String
    ): Call<SearchResponse>
}