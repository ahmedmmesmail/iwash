package com.iwash.app.core.network

import com.google.gson.annotations.SerializedName

data class ApiErrorModel(
    @SerializedName("status") val status: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("errors") val errors: ApiErrors? = null,
)

data class ApiErrors(
    @SerializedName("email") val email: List<String>? = null,
)
