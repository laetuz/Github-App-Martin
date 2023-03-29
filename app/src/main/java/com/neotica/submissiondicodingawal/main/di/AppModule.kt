package com.neotica.submissiondicodingawal.main.di

import androidx.room.Room
import com.neotica.submissiondicodingawal.mvvm.Constants
import com.neotica.submissiondicodingawal.retrofit.ApiService
import com.neotica.submissiondicodingawal.room.GithubDatabase
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val localModule = module{
    factory { get<GithubDatabase>().dao() }
    single{
        Room.databaseBuilder(androidContext(), GithubDatabase::class.java, "Github.db").build()
    }
}
val networkModule= module {
    single{
        val auth = "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"
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