package com.neotica.submissiondicodingawal.mvvm

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.response.UserDetailResponse
import com.neotica.submissiondicodingawal.retrofit.ApiConfig
import com.neotica.submissiondicodingawal.retrofit.ApiService
import com.neotica.submissiondicodingawal.room.Dao
import com.neotica.submissiondicodingawal.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepo private constructor(
    private val apiService: ApiService,
    private val dao: Dao,
    private val appExecutors: AppExecutors
    ) {
    //LiveData
    private val _githubResponse = MutableLiveData<List<GithubResponseItem>?>()
    val githubResponse: LiveData<List<GithubResponseItem>?> = _githubResponse
    private val _detailResponse = MutableLiveData<UserDetailResponse?>()
    val detailResponse: LiveData<UserDetailResponse?> = _detailResponse
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun getUser() {
        ApiConfig.getApiService().getUser().enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>,
            ) {
                if (response.isSuccessful) {
                    _githubResponse.value = response.body()
                    isLoading.value = false
                } else {
                    Log.e(ContentValues.TAG, "On failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                Log.e(ContentValues.TAG, "On failure: ${t.message}")
            }
        })
    }

    companion object{
        @Volatile
        private var instance: GithubRepo? = null
        fun getInstance(
            apiService: ApiService,
            dao: Dao,
            appExecutors: AppExecutors
        ):GithubRepo =
            instance ?: synchronized(this) {
                instance ?:GithubRepo(apiService,dao,appExecutors)
            }.also { instance = it }
    }
}