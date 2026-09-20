package com.iwash.app.core.network

import com.iwash.app.feature.register.data.model.RegisterRequest
import com.iwash.app.feature.register.data.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(ApiConstants.REGISTER)
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}
