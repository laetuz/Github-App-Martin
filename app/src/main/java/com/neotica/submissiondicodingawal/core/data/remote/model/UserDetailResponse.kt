package com.neotica.submissiondicodingawal.core.data.remote.model

data class UserDetailResponse(
    val followers: Int,
    val following: Int,
    val name: String,
    val login: String,
    val avatar: String
)