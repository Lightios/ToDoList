package pl.ppistudio.todolist.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.ppistudio.todolist.domain.Priority
import pl.ppistudio.todolist.domain.Task
import pl.ppistudio.todolist.domain.TaskRepository

class TaskDetailsViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadTask(taskId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _task.value = getSampleTasks().find { it.id == taskId }
            _isLoading.value = false
        }
    }

    fun updateTaskCompletionStatus(isCompleted: Boolean) {
        viewModelScope.launch {
            _task.value = _task.value?.copy(isCompleted = isCompleted)
        }
    }

    fun deleteTask() {
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
