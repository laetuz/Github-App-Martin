package com.neotica.submissiondicodingawal.mvvm.viemodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neotica.submissiondicodingawal.mvvm.Constants.API_TOKEN
import com.neotica.submissiondicodingawal.mvvm.GithubRepo
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.response.UserDetailResponse
import com.neotica.submissiondicodingawal.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(
    private val githubRepo: GithubRepo
) : ViewModel() {
    //LiveData
    private val _githubResponse = MutableLiveData<List<GithubResponseItem>?>()
    val githubResponse: LiveData<List<GithubResponseItem>?> = _githubResponse
    private val _detailResponse = MutableLiveData<UserDetailResponse?>()
    val detailResponse: LiveData<UserDetailResponse?> = _detailResponse

    fun getUser() {
        ApiConfig.getApiService().getUser(API_TOKEN).enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>,
            ) {
                if (response.isSuccessful) {
                    _githubResponse.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "On failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                Log.e(ContentValues.TAG, "On failure: ${t.message}")
            }
        })
    }
}