package hr.algebra.to_doapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.algebra.to_doapp.data.TaskEntity
import hr.algebra.to_doapp.ui.components.BottomNavBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    viewModel: TaskViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var datePickerVisible by remember { mutableStateOf(false) }
    var newTaskText by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val tasks by viewModel.tasks.collectAsState()
    val tasksForDay = tasks.filter { it.dueDate == selectedDate }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { datePickerVisible = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Outlined.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // TopBar
            Row(
                Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.DateRange, contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Text("Calendar", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.weight(1f))
                Button(onClick = { selectedDate = LocalDate.now() }) {
                    Text("Today")
                }
            }
            // Date Row & Button to Change Date
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
            ) {
                Text(
                    text = selectedDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd")),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(16.dp))
                Button(onClick = { datePickerVisible = true }) {
                    Text("Pick Date")
                }
            }
            // Tasks for selected day
            Text(
                "${tasksForDay.size} tasks scheduled",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 2.dp)
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tasksForDay, key = { it.id }) { task ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                            .background(
                                if (task.isDone) MaterialTheme.colorScheme.primary.copy(alpha = 0.09f)
                                else MaterialTheme.colorScheme.surface,
                                MaterialTheme.shapes.medium
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = { checked ->
                                viewModel.updateTask(task.copy(isDone = checked))
                            }
                        )
                        Column(Modifier.weight(1f)) {
                            Text(
                                task.title,
                                fontWeight = FontWeight.Medium,
                                textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                                color = when {
                                    !task.isDone && task.dueDate < LocalDate.now() -> Color.Red
                                    task.isDone -> MaterialTheme.colorScheme.primary
                                    else -> MaterialTheme.colorScheme.onBackground
                                }
                            )
                            Text(
                                "Due: ${task.dueDate.format(DateTimeFormatter.ofPattern("MMM dd"))}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
        if (datePickerVisible) {
            TaskAddDialog(
                onDismissRequest = { datePickerVisible = false },
                onAddTask = { date, text ->
                    if (text.isNotBlank()) {
                        viewModel.addTask(text, date)
                        datePickerVisible = false
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddDialog(
    onDismissRequest: () -> Unit,
    onAddTask: (LocalDate, String) -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    var newText by remember { mutableStateOf("") }
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                val millis = datePickerState.selectedDateMillis
                if (millis != null && newText.isNotBlank()) {
                    val pickedDate = LocalDate.ofEpochDay(millis / 86400000)
                    onAddTask(pickedDate, newText)
                }
            }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancel") }
        }
    ) {
        Column(Modifier.padding(16.dp)) {
            DatePicker(state = datePickerState)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = newText,
                onValueChange = { newText = it },
                label = { Text("Task Title") }
            )
        }
    }
}