package com.neotica.submissiondicodingawal.core.data.local.repo

import android.content.ContentValues
import android.util.Log
import com.neotica.submissiondicodingawal.core.data.remote.model.UserDetailResponse
import com.neotica.submissiondicodingawal.core.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailRepo(private val apiService: ApiService) {
    val detailResponse = MutableStateFlow<UserDetailResponse?>(null)
    val isLoading: MutableStateFlow<Boolean> by lazy { MutableStateFlow(false) }

    fun getUserDetail(name: String) {
        isLoading.value = true
        apiService.getUserDetail(name.ifEmpty { "null" })
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        isLoading.value = false
                        val responseBody = response.body()
                        detailResponse.value = responseBody
                    } else {
                        isLoading.value = false
                        Log.e(ContentValues.TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    isLoading.value = false
                    t.message?.let { Log.e(ContentValues.TAG, it) }
                }
            })
    }
}