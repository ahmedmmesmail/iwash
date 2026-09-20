package com.iwash.app.core.network

import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ApiErrorHandler {

    fun handle(error: Throwable): String {
        return when (error) {
            is SocketTimeoutException ->
                "Connection timed out. Please try again."

            is UnknownHostException ->
                "No internet connection. Please check your network."

            is HttpException -> {
                val body = error.response()?.errorBody()?.string()
                val parsed = body?.let {
                    runCatching { Gson().fromJson(it, ApiErrorModel::class.java) }.getOrNull()
                }
                parsed?.message ?: "Something went wrong. Please try again."
            }

            is IOException ->
                "No internet connection. Please check your network."

            else ->
                "An unexpected error occurred."
        }
    }
}
