package com.iwash.app.feature.register.data.repository

import com.iwash.app.core.network.ApiErrorHandler
import com.iwash.app.core.network.ApiResult
import com.iwash.app.core.network.ApiService
import com.iwash.app.core.storage.TokenManager
import com.iwash.app.feature.register.data.model.RegisterRequest

/** Equivalent of register_repo.dart. */
class RegisterRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
) {

    suspend fun register(request: RegisterRequest): ApiResult<String> {
        return try {
            val response = apiService.register(request)
            response.data?.token?.let { tokenManager.saveToken(it) }
            ApiResult.Success(response.message ?: "Registration successful")
        } catch (e: Exception) {
            ApiResult.Error(ApiErrorHandler.handle(e))
        }
    }
}
