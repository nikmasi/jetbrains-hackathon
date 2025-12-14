package com.example.tasko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tasko.screens.Destinations
import com.example.tasko.screens.auth.AccountScreen
import com.example.tasko.screens.auth.FirstScreen
import com.example.tasko.screens.auth.LoginScreen
import com.example.tasko.screens.auth.Project
import com.example.tasko.screens.auth.ProjectL
import com.example.tasko.screens.auth.ProjectListScreen
import com.example.tasko.screens.auth.ProjectScreen
import com.example.tasko.screens.auth.ProjectSettingsScreen
import com.example.tasko.screens.auth.RegistrationScreen
import com.example.tasko.ui.theme.TaskoTheme
import com.example.tasko.viewModels.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskoTheme {
                TaskoApp()
            }
        }
    }
}

@Composable
fun rememberCurrentRoute(navController: NavController): String {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    return currentDestination?.route ?: Destinations.LoginScreen.route
}

@Composable
fun TaskoApp() {
    val myViewModel: MyViewModel = hiltViewModel()
    val navController = rememberNavController()
    val currentRoute = rememberCurrentRoute(navController)

    val sampleProjects = listOf(
        Project("TODO", "This is project A"),
        Project("IN PROCESS", "This is project B"),
        Project("DONE", "This is project C")
    )

    val sampleProjects2 = listOf(
        ProjectL("Project A", "Description A"),
        ProjectL("Project B", "Description B"),
        ProjectL("Project C", "Description C")
    )
    val members = listOf("Ja","Mihajlo","Jaroslav")
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val padding = innerPadding

        NavHost(
            navController = navController,
            startDestination = Destinations.FirstScreen.route
        ) {
            composable(route = Destinations.LoginScreen.route){
                LoginScreen(navController=navController, myViewModel = myViewModel)
            }
            composable(route = Destinations.RegistrationScreen.route){
                RegistrationScreen(navController) { firstName, lastName, username, email, password ->
                    println("Register: $firstName $lastName, username=$username, email=$email")
                }
            }
            /*composable(route = Destinations.ProjectScreen.route){
                ProjectScreen(
                    projects = sampleProjects,
                    onAddProject = { /* Open dialog / screen for new project */ },
                    onAddItem = { project -> /* Open dialog to add item to project */ },
                    onSettingsClick = { navController.navigate(Destinations.ProjectSettingsScreen.route) },
                    onAccountClick = { navController.navigate(Destinations.AccountScreen.route) }
                )
            }*/
            composable(
                route = Destinations.ProjectScreen.route + "/{name}",
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType }
                )
            ) { navBackStackEntry ->

                val projectName = navBackStackEntry.arguments?.getString("name") ?: ""

                ProjectScreen(
                    projects = sampleProjects,
                    onAddProject = { /* Open dialog / screen for new project */ },
                    onAddItem = { project -> /* Open dialog to add item to project */ },
                    onSettingsClick = { navController.navigate(Destinations.ProjectSettingsScreen.route) },
                    onAccountClick = { navController.navigate(Destinations.AccountScreen.route) },
                    projectName=projectName,
                    myViewModel=myViewModel
                )
            }



            composable(route = Destinations.ProjectSettingsScreen.route){
                ProjectSettingsScreen(
                    "ProjectName",
                    members,
                    {navController.navigate(Destinations.ProjectScreen.route)}
                )
            }

            composable(route = Destinations.AccountScreen.route){
                val userName = "Masa"
                val userEmail = "masa@example.com"

                AccountScreen(
                    userName = userName,
                    userEmail = userEmail,
                    onBackClick = {
                        // Ovde ide logika za povratak na prethodni ekran
                        println("Back clicked")
                    },
                    onLogoutClick = {
                        // Ovde ide logika za logout
                        println("Logout clicked")
                    },
                    myViewModel = myViewModel
                )
            }
            composable(route= Destinations.FirstScreen.route){
                FirstScreen(
                    navController,myViewModel
                )
            }
            composable(route = Destinations.ProjectListScreen.route)
            {
                ProjectListScreen(
                    onProjectClick = { project ->
                        // npr. navigacija na detalje projekta
                        navController.navigate(Destinations.ProjectScreen.route)
                    },
                    projects2 = sampleProjects2,
                    myViewModel = myViewModel,
                    navController = navController
                )
            }


        }
    }
}
