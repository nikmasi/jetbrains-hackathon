package com.example.tasko.screens.auth

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Project(
    val title: String,
    val description: String
)

@Composable
fun ProjectScreen(projects: List<Project>, onAddProject: () -> Unit, onAddItem: (Project) -> Unit, onSettingsClick : ()->Unit,onAccountClick : ()->Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222222))
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Projects",
                color = Color.White,
                fontSize = 24.sp
            )
            Row {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                }
                IconButton(onClick =onAccountClick) {
                    Icon(Icons.Default.Person, contentDescription = "User Profile", tint = Color.White)
                }
            }
        }

        // Horizontal Projects
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(projects) { project ->
                ProjectCard(project = project, onAddItem = { onAddItem(project) })
            }
            item {
                EmptyProjectCard(onClick = { onAddProject() })
            }
        }
    }
}

@Composable
fun ProjectCard(project: Project, onAddItem: () -> Unit) {
    var isAddingTask by remember { mutableStateOf(false) }
    var taskText by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .width(250.dp)
            .heightIn(min = 180.dp), // heightIn da raste kada se doda TextField
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF444444))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Column {
                Text(
                    text = project.title,
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    text = project.description,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))



            Spacer(modifier = Modifier.height(8.dp))

            // Link "Add Task"
            if (!isAddingTask) {
                Text(
                    text = "+ Add Task",
                    color = Color.Cyan,
                    modifier = Modifier
                        .clickable(
                            indication = LocalIndication.current,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isAddingTask = true
                        }
                        .padding(top = 8.dp)
                )
            } else {
                // TextField za unos nove stavke
                OutlinedTextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    placeholder = { Text("Enter task") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (taskText.isNotBlank()) {
                                    onAddItem()
                                    taskText = ""
                                    isAddingTask = false
                                }
                            }
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Add Task")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EmptyProjectCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(180.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF555555))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Project",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
