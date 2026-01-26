package hr.algebra.to_doapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddDialog(
    onDismissRequest: () -> Unit,
    onAddTask: (LocalDate, String) -> Unit
) {
    val dateState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    var newText by remember { mutableStateOf("") }
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                dateState.selectedDateMillis?.let { millis ->
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
            DatePicker(state = dateState)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = newText,
                onValueChange = { newText = it },
                label = { Text("Task Title") }
            )
        }
    }
}