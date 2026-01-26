package hr.algebra.to_doapp.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    fun getTasks(): Flow<List<TaskEntity>> = dao.getAllTasks()
    suspend fun insert(task: TaskEntity) = dao.insert(task)
    suspend fun update(task: TaskEntity) = dao.update(task)
    suspend fun delete(task: TaskEntity) = dao.delete(task)
}