package com.neotica.submissiondicodingawal.response.testing

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)