package com.example.trendify.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.trendify.R
import com.example.trendify.presentation.viewmodel.RegisterViewModel
import java.util.regex.Pattern

class RegisterScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: RegisterViewModel = hiltViewModel()
        val registerResponse = viewModel.registerResponse.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        RegistrationScreen(
            onRegisterClick = { name, phone, email, password ->
                viewModel.register(
                    Name = name,
                    Email = email,
                    Password = password,
                    Phone = phone.toInt(),
                    onRegiserterSuccess = {
                        // Navigate to login screen on success
                        navigator.push(LoginSceen())
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(onRegisterClick: (String, String, String, String) -> Unit) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var phone by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var userClicked by remember { mutableStateOf(false) }

    // Validation states
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    // Navigator
    val navigator = LocalNavigator.currentOrThrow

    // Email validation regex
    val emailRegex = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$")

    // Password validation (minimum 6 characters)
    val isValidPassword = password.text.length >= 6

    // Phone validation (should be numeric and at least 10 digits)
    val isValidPhone = phone.text.length >= 10 && phone.text.all { it.isDigit() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Register") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFFA500)) // Orange color
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.register),
                contentDescription = "Register image",
                modifier = Modifier.size(300.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Phone Field
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                isError = !isValidPhone && phone.text.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            if (!isValidPhone && phone.text.isNotEmpty()) {
                Text("Invalid phone number", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !emailRegex.matcher(it.text).matches() && it.text.isNotEmpty()
                },
                label = { Text("Email") },
                isError = emailError,
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError) {
                Text("Invalid email address", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = !isValidPassword && it.text.isNotEmpty()
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError,
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError) {
                Text("Password must be at least 6 characters", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Register Button
            Button(
                onClick = {
                    if (isValidPhone && !emailError && isValidPassword) {
                        userClicked = true
                        onRegisterClick(
                            name.text,
                            phone.text,
                            email.text,
                            password.text
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
            ) {
                Text("Register")
            }

            // Loading indicator after button click
            AnimatedVisibility(visible = userClicked) {
                CircularProgressIndicator()
            }
        }
    }
}