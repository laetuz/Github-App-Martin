package com.neotica.submissiondicodingawal.core.data.local.repo

import android.content.ContentValues
import android.util.Log
import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.core.data.remote.retrofit.ApiService
import com.neotica.submissiondicodingawal.core.domain.following.IFollowingRepo
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingRepo(private val apiService: ApiService): IFollowingRepo {
    override val followingResponse = MutableStateFlow<List<GithubResponseItem>?>(null)
    override val isLoading: MutableStateFlow<Boolean> by lazy { MutableStateFlow(false) }

    override suspend fun getFollowing(name: String) {
        isLoading.value = true
        apiService.getFollowing(name.ifEmpty { "null" })
            .enqueue(object : Callback<List<GithubResponseItem>> {
                override fun onResponse(
                    call: Call<List<GithubResponseItem>>,
                    response: Response<List<GithubResponseItem>>,
                ) {
                    if (response.isSuccessful) {
                        isLoading.value = false
                        val responseBody = response.body()
                        followingResponse.value = responseBody
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