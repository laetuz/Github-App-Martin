package com.neotica.submissiondicodingawal.response

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<GithubResponseItem>,
    val total_count: Int
)