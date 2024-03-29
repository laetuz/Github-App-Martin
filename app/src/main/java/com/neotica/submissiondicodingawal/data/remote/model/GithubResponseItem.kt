package com.neotica.submissiondicodingawal.data.remote.model

import com.google.gson.annotations.SerializedName

data class GithubResponseItem(
    @SerializedName("avatar_url")
    val avatar_url: String,
    val events_url: String,
    @SerializedName("followers_url")
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)