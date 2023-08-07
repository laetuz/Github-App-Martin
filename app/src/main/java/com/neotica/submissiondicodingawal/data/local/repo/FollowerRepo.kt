package com.neotica.submissiondicodingawal.data.local.repo

import android.content.ContentValues
import android.util.Log
import com.neotica.submissiondicodingawal.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerRepo(private val apiService: ApiService) {
    val followersResponse = MutableStateFlow<List<GithubResponseItem>?>(null)
    val isLoading: MutableStateFlow<Boolean> by lazy { MutableStateFlow(false) }

    fun getFollowers(name: String) {
        isLoading.value = true
        apiService.getFollowers(name.ifEmpty { "null" })
            .enqueue(object : Callback<List<GithubResponseItem>> {
                override fun onResponse(
                    call: Call<List<GithubResponseItem>>,
                    response: Response<List<GithubResponseItem>>,
                ) {
                    if (response.isSuccessful) {
                        isLoading.value = false
                        val responseBody = response.body()
                        followersResponse.value = responseBody
                    } else {
                        isLoading.value = false
                        Log.e(ContentValues.TAG, "On failure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                    isLoading.value = false
                    Log.e(ContentValues.TAG, "On failure: ${t.message}")
                }
            })
    }
}