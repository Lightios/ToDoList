package pl.ppistudio.todolist.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import pl.ppistudio.todolist.domain.Task
import pl.ppistudio.todolist.domain.TaskRepository

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    private val _tasksFlow = MutableStateFlow(emptyList<Task>())
    override val tasksFlow: StateFlow<List<Task>> = _tasksFlow.asStateFlow()

    override suspend fun getAllTasks() {
        withContext(Dispatchers.IO) {
            _tasksFlow.update { _ ->
                taskDao.getAll()
            }
        }
    }

    override suspend fun getTask(id: String): Task? {
        return withContext(Dispatchers.IO) {
            _tasksFlow.value.find { it.id == id }
        }
    }

    override suspend fun createTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insert(task)
        }
        _tasksFlow.update { tasks ->
            tasks + task
        }
    }

    override suspend fun updateTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.update(task)
            _tasksFlow.update { tasks ->
                tasks.map { if (it.id == task.id) task else it }
            }
        }
    }

    override suspend fun deleteTask(id: String) {
        withContext(Dispatchers.IO) {
            val task = taskDao.getAll().find { it.id == id }
            if (task != null) {
                taskDao.delete(task)
            }
        }
        _tasksFlow.update { tasks ->
            tasks.filter { it.id != id }
        }
    }

    override suspend fun updateTaskCompletionStatus(task: Task, completed: Boolean) {
        withContext(Dispatchers.IO) {
            taskDao.update(task.id, completed)
        }

        _tasksFlow.update { tasks ->
            tasks.map { if (it.id == task.id) task.copy(isCompleted = completed) else it }
        }
    }
}
