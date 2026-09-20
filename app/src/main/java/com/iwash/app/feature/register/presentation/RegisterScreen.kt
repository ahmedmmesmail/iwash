package com.iwash.app.feature.register.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iwash.app.core.di.ViewModelFactory
import com.iwash.app.core.ui.AppButton
import com.iwash.app.core.ui.AppTextField
import com.iwash.app.feature.register.data.model.RegisterRequest
import kotlinx.coroutines.launch

/** Equivalent of register_screen.dart. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModel: RegisterViewModel = viewModel(factory = ViewModelFactory()),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.status) {
        when (state.status) {
            RegisterStatus.SUCCESS -> {
                scope.launch {
                    snackbarHostState.showSnackbar(state.message ?: "Registration successful")
                }
                onRegisterSuccess()
            }

            RegisterStatus.ERROR -> {
                scope.launch {
                    snackbarHostState.showSnackbar(state.message ?: "Registration failed")
                }
            }

            else -> Unit
        }
    }

    fun validate(): Boolean {
        nameError = if (name.trim().isEmpty()) "Please enter your name" else null
        emailError = when {
            email.trim().isEmpty() -> "Please enter your email"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Please enter a valid email"
            else -> null
        }
        passwordError = when {
            password.isEmpty() -> "Please enter your password"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
        confirmPasswordError = when {
            confirmPassword.isEmpty() -> "Please confirm your password"
            confirmPassword != password -> "Passwords do not match"
            else -> null
        }
        return listOf(nameError, emailError, passwordError, confirmPasswordError).all { it == null }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Register") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                "Create Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                "Please fill the details and create an account",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            AppTextField(
                label = "Name",
                value = name,
                onValueChange = { name = it },
                errorText = nameError,
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
            )
            AppTextField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
                keyboardType = KeyboardType.Email,
                errorText = emailError,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            )
            AppTextField(
                label = "Password",
                value = password,
                onValueChange = { password = it },
                isPassword = true,
                errorText = passwordError,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            )
            AppTextField(
                label = "Confirm Password",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isPassword = true,
                errorText = confirmPasswordError,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            )

            AppButton(
                text = "Register",
                isLoading = state.status == RegisterStatus.LOADING,
                onClick = {
                    if (validate()) {
                        viewModel.register(
                            RegisterRequest(
                                name = name.trim(),
                                email = email.trim(),
                                password = password,
                                confirmationPassword = confirmPassword,
                            ),
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
            )
        }
    }
}
