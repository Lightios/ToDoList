package pl.ppistudio.todolist.ui.add_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.ppistudio.todolist.domain.Priority
import pl.ppistudio.todolist.domain.Task
import pl.ppistudio.todolist.domain.TaskRepository

class AddEditTaskViewModel (
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _priority = MutableStateFlow(Priority.MEDIUM)
    val priority  = _priority.asStateFlow()

    private val _dueDate = MutableStateFlow<Long?>(null)
    val dueDate = _dueDate.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker = _showDatePicker.asStateFlow()

    private var taskId: String? = null

    fun loadTask(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            getSampleTasks().find { it.id == id }?.let { task ->
                taskId = task.id
                _title.value = task.title
                _description.value = task.description
                _priority.value = task.priority
                _dueDate.value = task.dueDate
            }
            _isLoading.value = false
        }
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setDescription(description: String) {
        _description.value = description
    }

    fun setPriority(priority: Priority) {
        _priority.value = priority
    }

    fun setDueDate(dueDate: Long?) {
        _dueDate.value = dueDate
    }

    fun setShowDatePicker(show: Boolean) {
        _showDatePicker.value = show
    }

    fun createTask() {
        // In a real app, this would save to the repository
    }

    fun updateTask() {
        // In a real app, this would update in the repository
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
