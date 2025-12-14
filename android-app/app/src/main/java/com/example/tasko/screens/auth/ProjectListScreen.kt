package com.example.tasko.screens.auth

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasko.data.retrofit.models.Project
import com.example.tasko.viewModels.MyViewModel

data class ProjectL(
    val title: String,
    val description: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(
    projects2: List<ProjectL>,
    onProjectClick: (Project) -> Unit,
    myViewModel: MyViewModel
) {
    val uiState by myViewModel.uiStateProjectsList.collectAsState()
    var showAddForm by remember { mutableStateOf(false) }
    var newProjectName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        myViewModel.fetchProjectsUser()
    }

    val projects = uiState.listProjects
    val projectItems = projects?.projects ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "All Projects", color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddForm = !showAddForm }) {
                Text("+")
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Forma za dodavanje novog projekta
                if (showAddForm) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Add New Project", color = Color.White, fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = newProjectName,
                                onValueChange = { newProjectName = it },
                                placeholder = { Text("Project Name") },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Gray,
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    if (newProjectName.isNotBlank()) {
                                        // Poziv ViewModel metode za dodavanje projekta
                                        myViewModel.createProject(newProjectName)
                                        myViewModel.fetchProjectsUser()
                                        newProjectName = ""
                                        showAddForm = false
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                            ) {
                                Text("Add Project", color = Color.White)
                            }
                        }
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(projectItems) { project ->
                        ProjectListCard(project = project, onClick = { onProjectClick(project) })
                    }
                }
            }
        }
    )
}

@Composable
fun ProjectListCard(project: Project, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF222222))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = project.name,
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
