package com.neotica.submissiondicodingawal.response.testing

import com.neotica.submissiondicodingawal.response.GithubResponseItem

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<GithubResponseItem>,
    val total_count: Int
)