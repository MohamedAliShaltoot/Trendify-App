package com.example.trendify.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.trendify.data.model.LoginResponse
import com.example.trendify.presentation.viewmodel.LoginViewModel

class LoginSceen : Screen {
    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = hiltViewModel()
        val LoginResponse by viewModel.loginResponse.collectAsState()
        val navigator = LocalNavigator.currentOrThrow


        LoginScreen(
            loginResponse = LoginResponse
        ) { email, password ->
            viewModel.login(email = email, password = password, onLoginSuccess = {
                navigator.push(HomeScreen())

            },
                onLoginFalier = {
                }
            )
        }


    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen(loginResponse: LoginResponse?, onLoginClick: (String, String) -> Unit) {
        var email by remember { mutableStateOf(TextFieldValue()) }
        var password by remember { mutableStateOf(TextFieldValue()) }
        var emailError by remember { mutableStateOf(false) }
        var passwordError by remember { mutableStateOf(false) }
        var emailErrorMessage by remember { mutableStateOf("") }
        var passwordErrorMessage by remember { mutableStateOf("") }
        var navigator = LocalNavigator.currentOrThrow

        // Email regex pattern
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Login") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(
                            0xFFFFA500
                        )
                    ) // Orange color
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
                    painter = painterResource(id = com.example.trendify.R.drawable.login),
                    contentDescription = "login image",
                    modifier = Modifier.size(300.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "Welcome Back!", fontSize = 30.sp, fontStyle = FontStyle.Italic)
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = false // Reset error on change
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = emailError // Highlight error in text field
                )

                // Email validation message
                if (emailError) {
                    Text(
                        text = emailErrorMessage,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = false // Reset error on change
                    },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    isError = passwordError // Highlight error in text field
                )

                // Password validation message
                if (passwordError) {
                    Text(
                        text = passwordErrorMessage,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Email validation
                        emailError = if (email.text.matches(emailPattern)) {
                            false
                        } else {
                            emailErrorMessage = "Invalid email format"
                            true
                        }

                        // Password validation (e.g., at least 6 characters)
                        passwordError = if (password.text.length >= 6) {
                            false
                        } else {
                            passwordErrorMessage = "Password must be at least 6 characters"
                            true
                        }

                        // Only proceed with login if there are no errors
                        if (!emailError && !passwordError) {
                            onLoginClick(
                                email.text,
                                password.text,
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFA500)
                    )
                ) {
                    Text("Login")
                }
                loginResponse?.let {
                    Text(text = it.message ,
                        color = if (it.message.equals("success")) Color.Green else Color.Red,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                TextButton(onClick = { /*TODO*/ }) {
                    Text("Forgot Password?")
                }
                Spacer(modifier = Modifier.height(5.dp))
                TextButton(onClick = { navigator.push(RegisterScreen())}) {
                    Text(text = "Don't have an account? Sign Up")
                }
            }
        }
    }
}