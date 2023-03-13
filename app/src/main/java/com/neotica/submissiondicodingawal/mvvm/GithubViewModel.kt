package com.neotica.submissiondicodingawal.mvvm

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neotica.submissiondicodingawal.response.GithubResponse
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel(
    private val githubRepo: GithubRepo
) : ViewModel() {
    //LiveData
    private val _githubResponse = MutableLiveData<List<GithubResponseItem>?>()
    val githubResponse: LiveData<List<GithubResponseItem>?> = _githubResponse

    var isFailureCaseMessage = MutableLiveData("")

    fun getUser() {
        ApiConfig.getApiService().getUser().enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _githubResponse.value = responseBody
                } else {
                    Log.e(ContentValues.TAG,"On failure: ${response.message()}")
                    // handle error here
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                Log.e(ContentValues.TAG,"On failure: ${t.message}")
                // handle error here
            }
        })
    }

    suspend fun getFollowers(name: String) {
        ApiConfig.getApiService().getFollowers(name).enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _githubResponse.value = responseBody
                } else {
                    Log.e(ContentValues.TAG,"On failure: ${response.message()}")
                    // handle error here
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                Log.e(ContentValues.TAG,"On failure: ${t.message}")
                // handle error here
            }
        })
    }
}