package pl.ppistudio.todolist.domain

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTask(id: String): Task?
    suspend fun createTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: String)
    suspend fun updateTaskCompletionStatus(task: Task, completed: Boolean)
    suspend fun getAllTasks(): Unit

    val tasksFlow: Flow<List<Task>>
}