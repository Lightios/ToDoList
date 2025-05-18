package pl.ppistudio.todolist.domain

interface TaskRepository {
    suspend fun getTask(id: String): Task?
    suspend fun getTasks(): List<Task>
    suspend fun createTask(task: Task): Task
    suspend fun updateTask(task: Task): Task
    suspend fun deleteTask(id: String)
}