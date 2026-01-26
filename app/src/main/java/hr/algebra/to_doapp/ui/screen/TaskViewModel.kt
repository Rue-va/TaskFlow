package hr.algebra.to_doapp.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import hr.algebra.to_doapp.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val db = TaskDatabase.getDatabase(application)
    private val repo = TaskRepository(db.taskDao())

    val tasks: StateFlow<List<TaskEntity>> = repo.getTasks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun addTask(title: String, date: LocalDate) {
        viewModelScope.launch {
            repo.insert(TaskEntity(title = title, dueDate = date))
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repo.delete(task)
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repo.update(task)
        }
    }
}