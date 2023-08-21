package com.neotica.submissiondicodingawal.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neotica.submissiondicodingawal.data.local.database.Dao
import com.neotica.submissiondicodingawal.data.local.database.Entity
import com.neotica.submissiondicodingawal.data.local.repo.FollowerRepo
import com.neotica.submissiondicodingawal.data.local.repo.SearchRepo
import com.neotica.submissiondicodingawal.data.local.repo.UserDetailRepo
import com.neotica.submissiondicodingawal.domain.following.FollowingInteractor
import com.neotica.submissiondicodingawal.domain.home.HomeInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GithubViewModel(
    private val homeInteractor: HomeInteractor,
    private val followingInteractor: FollowingInteractor,
    private val followerRepo: FollowerRepo,
    private val detailRepo: UserDetailRepo,
    private val searchRepo: SearchRepo,
    private val gitDao: Dao,
    application: Application
) : ViewModel() {
    //Home Screen
    val homeResponse = homeInteractor.userResponse
    val isLoadingHome = homeInteractor.isLoading

    //Following Screen
    val followingResponse = followingInteractor.followingResponse
    val isLoadingFollowing = followingInteractor.isLoading

    //Follower Screen
    val followerResponse = followerRepo.followersResponse
    val isLoadingFollower = followerRepo.isLoading

    //Detail Screen
    val detailResponse = detailRepo.detailResponse
    val isLoadingDetail = detailRepo.isLoading

    //Search Screen
    val searchResponse = searchRepo.searchResponse
    val isLoadingSearch = searchRepo.isLoading

    private val context = application

    fun getUser() {
        viewModelScope.launch {
            homeInteractor.fetchUser()
        }

    }

    fun getUserDetail(name: String) {
        detailRepo.getUserDetail(name)
    }

    fun getFollowers(name: String) {
        followerRepo.getFollowers(name)
    }

    suspend fun getFollowing(name: String) {
        followingInteractor.getFollowing(name)
    }

    fun getSearch(query: String) {
        searchRepo.getSearch(query)
    }

    /*End of response*/

    /*Bookmark*/
    fun getFavorite(): Flow<List<Entity>> {
        return gitDao.getGithub()
    }

    suspend fun setFavorite(entity: Entity) {
        withContext(Dispatchers.IO){
            entity.isBookmarked = true
            gitDao.insertBookmark(entity)
        }
    }

    suspend fun deleteUser(username: String) {
        withContext(Dispatchers.IO){
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

    fun getThemeSettings(): Flow<String> {
        return getThemeValue()
    }
    /*end of datastore*/

    companion object {
        private const val DATASTORE_THEME = "preferences"
        private val THEME_KEY = stringPreferencesKey("theme_key")
        private val Context.prefDataStore by preferencesDataStore(name = DATASTORE_THEME)
    }
}