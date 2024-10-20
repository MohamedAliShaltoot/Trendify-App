package com.example.trendify.presentation.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import com.example.trendify.R

class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        // Launch a coroutine to navigate after a delay
        LaunchedEffect(Unit) {
            delay(5000) // 2-second delay for the splash screen
            navigator.replace(LoginSceen()) // Navigate to HomeScreen or LoginScreen
        }

        SplashContent()
    }

    @Composable
    fun SplashContent() {
        // Define an infinite animation for scaling the logo
        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        // Define a fade-in animation for the app name
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            visible = true
        }

        val alpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = tween(durationMillis = 1000)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFA500)), // Orange background
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App Logo with scaling animation
                Image(
                    painter = painterResource(id = R.drawable.splash),
                    contentDescription = "Splash Logo",
                    modifier = Modifier
                        .size(300.dp)
                        .scale(scale)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // App Name with fade-in animation
                Text(
                    text = "Welcome to Trenday",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.alpha(alpha)
                )
            }
        }
    }
}