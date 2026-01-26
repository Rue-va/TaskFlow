package hr.algebra.to_doapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.format.DateTimeFormatter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import hr.algebra.to_doapp.data.TaskEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    task: TaskEntity,
    onCheckedChange: (Boolean) -> Unit,
    onStarClick: () -> Unit,
    onEditClick: () -> Unit // new!
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = onCheckedChange
            )
            Column(Modifier.weight(1f).padding(start = 16.dp)) {
                Text(
                    text = task.title,
                    fontWeight = if (task.isDone) FontWeight.Normal else FontWeight.Medium,
                    color = if (task.isDone)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    else MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (task.isDone)
                        TextDecoration.LineThrough else TextDecoration.None
                )
                val formattedDueDate = task.dueDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                Text(formattedDueDate, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onStarClick) {
                Icon(
                    imageVector = if (task.isImportant) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (task.isImportant)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = onEditClick) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}