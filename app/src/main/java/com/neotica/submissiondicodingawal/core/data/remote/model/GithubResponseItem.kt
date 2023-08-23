package com.neotica.submissiondicodingawal.core.data.remote.model

import com.google.gson.annotations.SerializedName

data class GithubResponseItem(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
)