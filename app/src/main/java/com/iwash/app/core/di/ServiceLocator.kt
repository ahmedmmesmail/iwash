package com.iwash.app.core.di

import android.content.Context
import com.iwash.app.core.network.ApiService
import com.iwash.app.core.network.RetrofitFactory
import com.iwash.app.core.storage.TokenManager
import com.iwash.app.feature.register.data.repository.RegisterRepository

/**
 * Simple manual service locator, equivalent to the Flutter app's
 * GetIt-based service_locaor.dart.
 */
object ServiceLocator {

    private lateinit var appContext: Context

    val tokenManager: TokenManager by lazy { TokenManager(appContext) }

    private val apiService: ApiService by lazy {
        RetrofitFactory.getRetrofit(tokenManager).create(ApiService::class.java)
    }

    val registerRepository: RegisterRepository by lazy {
        RegisterRepository(apiService, tokenManager)
    }

    fun init(context: Context) {
        appContext = context.applicationContext
        tokenManager.loadCachedTokenBlocking()
    }
}
