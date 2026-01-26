package hr.algebra.to_doapp.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.algebra.to_doapp.R
import hr.algebra.to_doapp.data.TaskEntity
import hr.algebra.to_doapp.ui.components.TaskCard
import hr.algebra.to_doapp.ui.components.BottomNavBar
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    viewModel: TaskViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<TaskEntity?>(null) }
    var newTaskText by remember { mutableStateOf("") }
    var editTask by remember { mutableStateOf<TaskEntity?>(null) }
    var editText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.my_tasks)) },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart) {
                                taskToDelete = task
                                showDialog = true
                            }
                            false
                        }
                    )
                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            val color =
                                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart)
                                    Color.Red
                                else
                                    Color.Transparent
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(16.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    ) {
                        TaskCard(
                            task = task,
                            onCheckedChange = { checked ->
                                viewModel.updateTask(task.copy(isDone = checked))
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        if (checked) "Task completed!" else "Task marked incomplete!"
                                    )
                                }
                            },
                            onStarClick = {
                                viewModel.updateTask(task.copy(isImportant = !task.isImportant))
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        if (!task.isImportant) "Marked as important!" else "Unmarked as important!"
                                    )
                                }
                            },
                            onEditClick = {
                                editTask = task
                                editText = task.title
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            // Add Task Row
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newTaskText,
                    onValueChange = { newTaskText = it },
                    placeholder = { Text("Add a new task...") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (newTaskText.isNotBlank()) {
                            viewModel.addTask(newTaskText, date = LocalDate.now())
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Task added!")
                            }
                            newTaskText = ""
                        }
                    }
                ) {
                    Text("+")
                }
            }
        }
        // Delete Dialog
        if (showDialog && taskToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteTask(taskToDelete!!)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Task deleted!")
                        }
                        showDialog = false
                        taskToDelete = null
                    }) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        taskToDelete = null
                    }) { Text("Cancel") }
                },
                title = { Text("Delete Task") },
                text = { Text("Are you sure you want to delete this task?" )}
            )
        }

        // Edit Dialog
        if (editTask != null) {
            AlertDialog(
                onDismissRequest = { editTask = null },
                title = { Text("Edit Task") },
                text = {
                    TextField(
                        value = editText,
                        onValueChange = { editText = it },
                        label = { Text("New Title") }
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (editText.isNotBlank()) {
                            viewModel.updateTask(editTask!!.copy(title = editText))
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Task updated!")
                            }
                        }
                        editTask = null
                    }) { Text("Save") }
                },
                dismissButton = {
                    TextButton(onClick = { editTask = null }) { Text("Cancel") }
                }
            )
        }
    }
}