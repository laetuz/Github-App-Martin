package com.neotica.submissiondicodingawal.mvvm

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.response.SearchResponse
import com.neotica.submissiondicodingawal.response.UserDetailResponse
import com.neotica.submissiondicodingawal.retrofit.ApiConfig
import com.neotica.submissiondicodingawal.retrofit.ApiService
import com.neotica.submissiondicodingawal.room.Dao
import com.neotica.submissiondicodingawal.room.Entity
import com.neotica.submissiondicodingawal.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.neotica.submissiondicodingawal.room.Result

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
    private val result =
        MediatorLiveData<List<GithubResponseItem>>()

    fun getBookmarks(): LiveData<List<Entity>>{
        return dao.getBookmarked()
    }
    fun setBookmarks(github: Entity, bookmarkState: Boolean){
        appExecutors.diskIO.execute {
            github.isBookmarked = bookmarkState
            dao.updateBookmark(github)
        }
    }

    fun getUser():LiveData<List<GithubResponseItem>> {
        val client = apiService.getUser()
        client.enqueue(object : Callback<List<GithubResponseItem>> {
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
        return result
    }

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