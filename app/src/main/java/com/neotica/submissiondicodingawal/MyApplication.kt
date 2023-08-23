package com.neotica.submissiondicodingawal

import android.app.Application
import com.neotica.submissiondicodingawal.core.di.localModule
import com.neotica.submissiondicodingawal.core.di.networkModule
import com.neotica.submissiondicodingawal.core.di.repositoryModule
import com.neotica.submissiondicodingawal.core.di.useCase
import com.neotica.submissiondicodingawal.core.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@MyApplication)
            modules(
                networkModule, localModule, viewModule, repositoryModule, useCase
            )
        }
    }
}