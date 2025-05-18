package pl.ppistudio.todolist.data

import pl.ppistudio.todolist.domain.Task
import pl.ppistudio.todolist.domain.TaskRepository

class TaskRepositoryImpl: TaskRepository {
    // In a real app, this would interact with a database or API
    // For this example, we'll use in-memory storage

    private val tasks = mutableListOf<Task>()

    override suspend fun getTask(id: String): Task? {
        return tasks.find { it.id == id }
    }

    override suspend fun getTasks(): List<Task> {
        return tasks.toList()
    }

    override suspend fun createTask(task: Task): Task {
        tasks.add(task)
        return task
    }

    override suspend fun updateTask(task: Task): Task {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
        }
        return task
    }

    override suspend fun deleteTask(id: String) {
        tasks.removeIf { it.id == id }
    }
}
