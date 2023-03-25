package com.neotica.submissiondicodingawal.mvvm.viemodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.response.SearchResponse
import com.neotica.submissiondicodingawal.response.UserDetailResponse
import com.neotica.submissiondicodingawal.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(
) : ViewModel() {
    //LiveData
    private val _githubResponse = MutableLiveData<List<GithubResponseItem>?>()
    val githubResponse: LiveData<List<GithubResponseItem>?> = _githubResponse
    private val _detailResponse = MutableLiveData<UserDetailResponse?>()
    val detailResponse: LiveData<UserDetailResponse?> = _detailResponse

    fun getSearch(query: String) {
        ApiConfig.getApiService().searchUser(query.ifEmpty { "null" }).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _githubResponse.value = responseBody?.items
                } else {
                    Log.e(ContentValues.TAG, "Search failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "Search : ${t.message}")
            }
        })
    }
}