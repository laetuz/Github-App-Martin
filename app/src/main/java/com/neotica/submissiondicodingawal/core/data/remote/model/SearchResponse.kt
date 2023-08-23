package com.neotica.submissiondicodingawal.core.data.remote.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<GithubResponseItem>,
    @SerializedName("total_count")
    val totalCount: Int
)