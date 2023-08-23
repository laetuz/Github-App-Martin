package com.neotica.submissiondicodingawal.data.local.repo

import android.content.ContentValues
import android.util.Log
import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.core.data.remote.retrofit.ApiService
import com.neotica.submissiondicodingawal.core.domain.home.IHomeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepo(
    private val apiService: ApiService
): IHomeRepo {
    override val userResponse = MutableStateFlow<List<GithubResponseItem>?>(null)
    override val isLoading: MutableStateFlow<Boolean> by lazy { MutableStateFlow(false) }

    override suspend fun getUser() {
        isLoading.value = true
        apiService.getUser()
            .enqueue(object : Callback<List<GithubResponseItem>> {
                override fun onResponse(
                    call: Call<List<GithubResponseItem>>,
                    response: Response<List<GithubResponseItem>>,
                ) {
                    if (response.isSuccessful) {
                        userResponse.value = response.body()
                        isLoading.value = false
                        Log.d("neo-tag", response.body().toString())
                    } else {
                        isLoading.value = false
                        userResponse.value = response.body()
                        Log.e(ContentValues.TAG, "On failure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                    Log.e(ContentValues.TAG, "On failure: ${t.message}")
                    isLoading.value = false
                }
            })
    }
}