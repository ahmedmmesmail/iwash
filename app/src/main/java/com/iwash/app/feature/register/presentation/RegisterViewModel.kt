package com.iwash.app.feature.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwash.app.core.network.ApiResult
import com.iwash.app.feature.register.data.model.RegisterRequest
import com.iwash.app.feature.register.data.repository.RegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Equivalent of register_cubit.dart. */
class RegisterViewModel(
    private val repository: RegisterRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterUiState())
    val state: StateFlow<RegisterUiState> = _state.asStateFlow()

    fun register(request: RegisterRequest) {
        _state.value = _state.value.copy(status = RegisterStatus.LOADING)
        viewModelScope.launch {
            when (val result = repository.register(request)) {
                is ApiResult.Success ->
                    _state.value = RegisterUiState(RegisterStatus.SUCCESS, result.data)

                is ApiResult.Error ->
                    _state.value = RegisterUiState(RegisterStatus.ERROR, result.message)
            }
        }
    }
}
