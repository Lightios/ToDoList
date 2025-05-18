package pl.ppistudio.todolist.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.ppistudio.todolist.domain.Priority
import pl.ppistudio.todolist.domain.Task
import pl.ppistudio.todolist.domain.TaskRepository

class TaskListViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>>  = _tasks.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            _tasks.value = getSampleTasks()
            _isLoading.value = false
        }
    }

    fun updateTaskCompletionStatus(taskId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            val updatedTasks = _tasks.value.map { task ->
                if (task.id == taskId) {
                    task.copy(isCompleted = isCompleted)
                } else {
                    task
                }
            }
            _tasks.value = updatedTasks
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            _tasks.value = _tasks.value.filter { it.id != taskId }
        }
    }

    // Sample data for demonstration
    private fun getSampleTasks(): List<Task> {
        return listOf(
            Task(
                id = "1",
                title = "Complete Android Project",
                description = "Finish the to-do app implementation with Jetpack Compose",
                priority = Priority.HIGH,
                dueDate = System.currentTimeMillis() + 86400000 // tomorrow
            ),
            Task(
                id = "2",
                title = "Buy groceries",
                description = "Milk, eggs, bread, fruits",
                priority = Priority.MEDIUM,
                dueDate = System.currentTimeMillis() + 172800000 // day after tomorrow
            ),
            Task(
                id = "3",
                title = "Call mom",
                priority = Priority.LOW
            ),
            Task(
                id = "4",
                title = "Prepare presentation",
                description = "Create slides for the team meeting",
                priority = Priority.HIGH,
                dueDate = System.currentTimeMillis() + 259200000 // 3 days from now
            ),
            Task(
                id = "5",
                title = "Go for a run",
                priority = Priority.MEDIUM,
                isCompleted = true
            )
        )
    }
}
