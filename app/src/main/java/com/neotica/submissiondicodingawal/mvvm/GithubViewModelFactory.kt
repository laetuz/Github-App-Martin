package com.neotica.submissiondicodingawal.mvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.neotica.submissiondicodingawal.di.Injection

class GithubViewModelFactory private constructor(private val githubRepo: GithubRepo) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(GithubViewModel::class.java)){
            return GithubViewModel(githubRepo) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class: " + modelClass.name)
    }
    private val githubRepoManager = GithubRepoManager()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GithubViewModel(githubRepo) as T
    }

    companion object {
        @Volatile
        private var instance: GithubViewModelFactory?= null
        fun getInstance(context: Context): GithubViewModelFactory =
            instance ?: synchronized(this){
                instance ?: GithubViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}