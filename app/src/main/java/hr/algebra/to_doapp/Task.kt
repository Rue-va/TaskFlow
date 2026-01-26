package hr.algebra.to_doapp

data class Task(
    val id: Int,
    val title: String,
    val isDone: Boolean = false,
    val isImportant: Boolean = false,
    val dueDate: String = ""
)