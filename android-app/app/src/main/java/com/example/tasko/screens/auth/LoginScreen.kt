package com.example.tasko.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavController
import com.example.tasko.screens.Destinations
import com.example.tasko.viewModels.MyViewModel

@Composable
fun LoginScreen(navController: NavController, myViewModel: MyViewModel){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val uiState by myViewModel.uiState.collectAsState()

    // Ako je uspešan login, navigiraj
    if (uiState.isLoggedIn) {
        LaunchedEffect(Unit) {
            navController.navigate(Destinations.ProjectListScreen.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222222)), // tamna pozadina
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(min = 280.dp, max = 320.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login!",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.White) },
                singleLine = true,
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                singleLine = true,
                visualTransformation =  VisualTransformation.None ,
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Default.Add
                    else Icons.Default.Menu

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle password visibility", tint = Color.LightGray)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    myViewModel.fetchLogin(username,password)
                    //navController.navigate(Destinations.ProjectScreen.route)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF444444))
            ) {
                Text("Login", color = Color.White)
            }
            TextButton(
                onClick = {
                    navController.navigate(Destinations.RegistrationScreen.route)
                },
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Text(
                    text = "Register",
                    color = Color.LightGray,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
fun SuggestionInput(
    onSubmit: (String) -> Unit,
    onCancel: () -> Unit
) {
    var suggestionText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF333333))
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        OutlinedTextField(
            value = suggestionText,
            onValueChange = { suggestionText = it },
            placeholder = { Text("Enter your suggestion...") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5,

        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = {
                suggestionText = ""
                onCancel()
            }) {
                Text("Cancel", color = Color.LightGray)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (suggestionText.isNotBlank()) {
                        onSubmit(suggestionText)
                        suggestionText = ""
                    }
                }
            ) {
                Text("Submit")
            }
        }
    }
}
