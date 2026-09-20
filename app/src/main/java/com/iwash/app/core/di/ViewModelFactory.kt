package com.iwash.app.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iwash.app.feature.register.presentation.RegisterViewModel

/**
 * Minimal ViewModelProvider.Factory so ViewModels can take constructor
 * dependencies from the ServiceLocator, without pulling in Hilt/Koin.
 */
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            RegisterViewModel::class.java ->
                RegisterViewModel(ServiceLocator.registerRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}
