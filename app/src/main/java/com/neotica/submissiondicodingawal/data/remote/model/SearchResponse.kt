package com.neotica.submissiondicodingawal.data.remote.model

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<GithubResponseItem>,
    val total_count: Int
)