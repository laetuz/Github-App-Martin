package com.neotica.submissiondicodingawal.viewmodel

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.neotica.submissiondicodingawal.data.local.repo.HomeRepo
import com.neotica.submissiondicodingawal.view.di.diskIO
import com.neotica.submissiondicodingawal.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.data.remote.model.SearchResponse
import com.neotica.submissiondicodingawal.data.remote.model.UserDetailResponse
import com.neotica.submissiondicodingawal.data.remote.retrofit.ApiService
import com.neotica.submissiondicodingawal.data.local.database.Dao
import com.neotica.submissiondicodingawal.data.local.database.Entity
import com.neotica.submissiondicodingawal.data.local.repo.FollowerRepo
import com.neotica.submissiondicodingawal.data.local.repo.FollowingRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GithubViewModel(
    private val homeRepo: HomeRepo,
    private val followingRepo: FollowingRepo,
    private val followerRepo: FollowerRepo,
    private val apiService: ApiService,
    private val gitDao: Dao,
    application: Application
) : ViewModel() {
    //Home Screen
    val homeResponse = homeRepo.userResponse
    val isLoadingHome = homeRepo.isLoading

    //Following Screen
    val isLoadingFollowing = followingRepo.isLoading
    val followingResponse = followingRepo.followingResponse

    //Follower Screen
    val isLoadingFollower = followerRepo.isLoading
    val followerResponse = followerRepo.followersResponse

    //Search Screen
    private val _githubResponse1 = MutableStateFlow<List<GithubResponseItem>?>(null)
    val githubResponse1: StateFlow<List<GithubResponseItem>?> = _githubResponse1

    private val _detailResponse = MutableStateFlow<UserDetailResponse?>(null)
    val detailResponse: StateFlow<UserDetailResponse?> = _detailResponse
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    private val context = application

    /*Bookmark*/
    fun getFavorite(): Flow<List<Entity>> {
        return gitDao.getGithub()
    }

    fun setFavorite(entity: Entity) {
        diskIO.execute {
            entity.isBookmarked = true
            gitDao.insertBookmark(entity)
        }
    }

    fun deleteUser(username: String) {
        diskIO.execute {
            gitDao.deleteUser(username)
        }
    }
    /*End of Bookmark*/

    /*datastore*/
    private suspend fun setTheme(currentTheme: String) {
        context.prefDataStore.edit { it[THEME_KEY] = currentTheme }
    }

    fun saveThemeSetting(currentTheme: String) =
        viewModelScope.launch(Dispatchers.IO) {
            setTheme(currentTheme)
        }

    private fun getThemeValue(): Flow<String> {
        return context.prefDataStore.data.map {
            it[THEME_KEY] ?: "default"
        }
    }

    fun getThemeSettings(): LiveData<String> {
        return getThemeValue().asLiveData()
    }
    /*end of datastore*/

    fun getUser() {
        homeRepo.getUser()
    }

    /*Response api*/
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
        followerRepo.getFollowers(name)
    }

    fun getFollowing(name: String) {
        followingRepo.getFollowing(name)
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
                        _githubResponse1.value = responseBody?.items
                        Log.d("neo-search", responseBody.toString())
                    } else {
                        Log.e(ContentValues.TAG, "Search failure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "Search : ${t.message}")
                }
            })
    }

    /*End of response*/
    companion object {
        private const val DATASTORE_THEME = "preferences"
        private val THEME_KEY = stringPreferencesKey("theme_key")
        private val Context.prefDataStore by preferencesDataStore(name = DATASTORE_THEME)
    }
}