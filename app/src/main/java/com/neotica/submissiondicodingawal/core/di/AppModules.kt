package com.neotica.submissiondicodingawal.core.di

import androidx.room.Room
import com.neotica.submissiondicodingawal.BuildConfig
import com.neotica.submissiondicodingawal.core.data.local.database.LocalDataSource
import com.neotica.submissiondicodingawal.core.data.remote.RemoteDataSource
import com.neotica.submissiondicodingawal.core.data.utils.Constants
import com.neotica.submissiondicodingawal.core.data.remote.retrofit.ApiService
import com.neotica.submissiondicodingawal.core.data.local.database.GithubDatabase
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val localModule = module{
    single { get<GithubDatabase>().dao() }
    single{
        Room.databaseBuilder(androidContext(), GithubDatabase::class.java, "Github.db").build()
    }
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
}
val networkModule= module {
    single{
        val auth = BuildConfig.AUTH_TOKEN
        OkHttpClient.Builder()
            .addInterceptor{chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization","token $auth")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}