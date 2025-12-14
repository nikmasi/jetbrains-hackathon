package com.example.tasko.screens.auth

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.example.tasko.data.retrofit.models.TaskLists
import com.example.tasko.viewModels.MyViewModel
import com.example.tasko.viewModels.UiStateListTasks

data class Project(
    val title: String,
    val description: String
)

@Composable
fun ProjectScreen(projects: List<Project>, onAddProject: () -> Unit, onAddItem: (Project) -> Unit, onSettingsClick : ()->Unit,onAccountClick : ()->Unit,projectName:String,myViewModel: MyViewModel) {

    val uiState by myViewModel.uiStateTasksListList.collectAsState()
    val uiStateL by myViewModel.uiStateTasksL.collectAsState()
    val tasksList = uiState.listTasks
    val tasksItems = tasksList?.taskst ?: emptyList()

    val tasksMap by myViewModel.tasksByTaskListId.collectAsState()





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
                text = "$projectName",
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
            items(tasksItems) { project ->
                ProjectCard(project = project, onAddItem = {  },uiStateL)
            }
            item {
                EmptyProjectCard(onClick = { onAddProject() })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "AI Suggestions",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        val aiResult by myViewModel.aiResult.collectAsState()


        if (aiResult == null) {

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                item {
                    AiSuggestionCard(
                        title = "Generate To Do tasks",
                        subtitle = "Suggest tasks for this project $projectName",
                        onClick = {

                        },
                        myViewModel
                    )

                }

                item {
                    AiSuggestionCard(
                        title = "Estimate tasks duration",
                        subtitle = "How long will tasks take?",
                        onClick = {

                        },
                        myViewModel
                    )
                }

                item {
                    AiSuggestionCard(
                        title = "Plan 7-day sprint",
                        subtitle = "Sprint planning based on tasks",
                        onClick = {

                        },
                        myViewModel
                    )
                }


            }

        } else {

            // 🧠 AI RESULT VIEW
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = aiResult!!,
                        color = Color.White,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "← Back to AI suggestions",
                        color = Color.Cyan,

                    )
                    Button(onClick = {
                        myViewModel.resetAiResult()
                    }) {Text("back") }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text("OR")
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Your Suggestion",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val aiResult2 by myViewModel.aiResult2.collectAsState()
        var suggestion by remember { mutableStateOf("") }

        SimpleInputWithButton(
            inputText = suggestion,
            onInputChange = { suggestion = it },
            onButtonClick = {
                myViewModel.sendAiPrompt2("I am developer. Response in 3 sentences. "+suggestion)
            },

        )
        if(aiResult2!=null){
            Text(" ${aiResult2.toString()}",color=Color.White)

        }





    }
}

@Composable
fun ProjectCard(project: TaskLists, onAddItem: () -> Unit,uiStateL: UiStateListTasks) {
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
                    text = project.name,
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    text = project.id_project.toString(),
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

                uiStateL.lista?.tasks?.forEach {
                    if(it.id_task_list==project.id){
                        Text(
                            text = "• ${it.title}",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
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
@Composable
fun AiSuggestionCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    myViewModel: MyViewModel
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(100.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = {  myViewModel.sendAiPrompt("I am developer. Response in 5-6 sentences. "+title+subtitle) }
            ),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF444444)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp
            )
            Text(
                text = subtitle,
                color = Color.LightGray,
                fontSize = 12.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleInputWithButton(
    inputText: String,
    onInputChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = onInputChange,
            label = { Text("Enter your suggestion") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                // Use focusedTextColor and unfocusedTextColor for the input text
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,

                // ... other color parameters for Material 3
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onButtonClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Send")
        }
    }
}

