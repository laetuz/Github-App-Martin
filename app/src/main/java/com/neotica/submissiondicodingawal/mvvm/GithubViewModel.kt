package com.neotica.submissiondicodingawal.mvvm

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neotica.submissiondicodingawal.main.di.diskIO
import com.neotica.submissiondicodingawal.main.utils.AppExecutors
import com.neotica.submissiondicodingawal.mvvm.Constants.API_TOKEN
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.response.SearchResponse
import com.neotica.submissiondicodingawal.response.UserDetailResponse
import com.neotica.submissiondicodingawal.retrofit.ApiConfig
import com.neotica.submissiondicodingawal.retrofit.ApiService
import com.neotica.submissiondicodingawal.room.Dao
import com.neotica.submissiondicodingawal.room.Entity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel(
    private val apiService: ApiService,
    private val gitDao: Dao,
) : ViewModel() {
    //LiveData
    private val _githubResponse = MutableLiveData<List<GithubResponseItem>?>()
    val githubResponse: LiveData<List<GithubResponseItem>?> = _githubResponse
    private val _detailResponse = MutableLiveData<UserDetailResponse?>()
    val detailResponse: LiveData<UserDetailResponse?> = _detailResponse
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun getFavorite(): LiveData<List<Entity>> {
        return gitDao.getGithub()
    }
    fun setFavorite(entity: Entity, state: Boolean){
        diskIO.execute {
            entity.isBookmarked = true
            gitDao.insertBookmark(entity)
        }
    }

    fun getUser() {
        apiService.getUser()
            .enqueue(object : Callback<List<GithubResponseItem>> {
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

    fun getUserDetail(name: String) {
        apiService.getUserDetail(name.ifEmpty { "null" })
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        _detailResponse.value = responseBody
                    } else {
                        Log.e(ContentValues.TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    t.message?.let { Log.e(ContentValues.TAG, it) }
                }
            })
    }

    fun getFollowers(name: String) {
        apiService.getFollowers(name.ifEmpty { "null" })
            .enqueue(object : Callback<List<GithubResponseItem>> {
                override fun onResponse(
                    call: Call<List<GithubResponseItem>>,
                    response: Response<List<GithubResponseItem>>,
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        _githubResponse.value = responseBody
                    } else {
                        Log.e(ContentValues.TAG, "On failure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                    Log.e(ContentValues.TAG, "On failure: ${t.message}")
                }
            })
    }

    fun getFollowing(name: String) {
        apiService.getFollowing(name.ifEmpty { "null" })
            .enqueue(object : Callback<List<GithubResponseItem>> {
                override fun onResponse(
                    call: Call<List<GithubResponseItem>>,
                    response: Response<List<GithubResponseItem>>,
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        _githubResponse.value = responseBody
                    } else {
                        Log.e(ContentValues.TAG, "On failure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                    Log.e(ContentValues.TAG, "On failure: ${t.message}")
                }
            })
    }

    fun getSearch(query: String) {
        apiService.searchUser(query.ifEmpty { "null" })
            .enqueue(object : Callback<SearchResponse> {
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