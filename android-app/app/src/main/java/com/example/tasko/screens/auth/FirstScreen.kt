package com.example.tasko.screens.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tasko.screens.Destinations
import com.example.tasko.viewModels.MyViewModel
import kotlinx.coroutines.delay

@Composable
fun FirstScreen(navController: NavController, myViewModel: MyViewModel) {
    val uiState by myViewModel.uiState.collectAsState()

    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(Unit) {
        delay(1000L)
        if (uiState.isLoggedIn) {
            navController.navigate(Destinations.ProjectListScreen.route) {
                popUpTo(Destinations.LoginScreen.route) { inclusive = true }
            }
        } else {
            navController.navigate(Destinations.LoginScreen.route) {
                popUpTo(Destinations.LoginScreen.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222222)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Tasko",
                fontSize = 36.sp,
                color = Color.Cyan,
                modifier = Modifier.rotate(rotation)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Cyan, shape = CircleShape)
            )
        }
    }
}