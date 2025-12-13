package com.example.tasko.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Destinations(val name:String, val route:String,val icon:ImageVector){
    object MainScreen:Destinations(
        name="MainScreen",
        route="MainScreen",
        icon = Icons.Default.FavoriteBorder
    )
    object LoginScreen:Destinations(
        name="LoginScreen",
        route="LoginScreen",
        icon = Icons.Default.FavoriteBorder
    )

    object RegistrationScreen:Destinations(
        name="RegistrationScreen",
        route="RegistrationScreen",
        icon = Icons.Default.FavoriteBorder
    )
    object ProjectScreen:Destinations(
        name="ProjectScreen",
        route="ProjectScreen",
        icon = Icons.Default.FavoriteBorder
    )

    object ProjectSettingsScreen:Destinations(
        name="ProjectSettingsScreen",
        route="ProjectSettingsScreen",
        icon = Icons.Default.FavoriteBorder
    )
    object AccountScreen:Destinations(
        name="AccountScreen",
        route="AccountScreen",
        icon = Icons.Default.FavoriteBorder
    )
    object FirstScreen:Destinations(
        name="FirstScreen",
        route="FirstScreen",
        icon = Icons.Default.FavoriteBorder
    )
    //AccountScreen
}