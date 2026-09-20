package com.iwash.app.feature.register.data.model

import com.google.gson.annotations.SerializedName

/** Equivalent of register_requires_model.dart. */
data class RegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("password_confirmation") val confirmationPassword: String,
)

data class RegisterResponse(
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: RegisterData? = null,
)

data class RegisterData(
    @SerializedName("token") val token: String? = null,
)
