package com.neotica.submissiondicodingawal.data.local.repo

import android.content.ContentValues
import android.util.Log
import com.neotica.submissiondicodingawal.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.data.remote.model.SearchResponse
import com.neotica.submissiondicodingawal.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepo(private val apiService: ApiService) {
    val searchResponse = MutableStateFlow<List<GithubResponseItem>?>(null)
    val isLoading: MutableStateFlow<Boolean> by lazy { MutableStateFlow(false) }

    fun getSearch(query: String) {
        isLoading.value = true
        apiService.searchUser(query.ifEmpty { "null" })
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        isLoading.value = false
                        val responseBody = response.body()
                        searchResponse.value = responseBody?.items
                        Log.d("neo-search", responseBody.toString())
                    } else {
                        isLoading.value = false
                        Log.e(ContentValues.TAG, "Search failure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    isLoading.value = false
                    Log.e(ContentValues.TAG, "Search : ${t.message}")
                }
            })
    }
}