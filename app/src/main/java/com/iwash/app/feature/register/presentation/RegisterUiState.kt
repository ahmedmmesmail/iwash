package com.iwash.app.feature.register.presentation

/** Equivalent of register_state.dart's Status enum + RegisterState. */
enum class RegisterStatus { INITIAL, LOADING, SUCCESS, ERROR }

data class RegisterUiState(
    val status: RegisterStatus = RegisterStatus.INITIAL,
    val message: String? = null,
)
