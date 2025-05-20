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
            val task = taskRepository.getTask(id)
            task?.let {
                _title.value = it.title
                _description.value = it.description
                _priority.value = it.priority
                _dueDate.value = it.dueDate
                taskId = it.id
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
        viewModelScope.launch {
            taskRepository.createTask(
                Task(
                    title = title.value,
                    description = description.value,
                    priority = priority.value,
                    dueDate = dueDate.value
                )
            )
        }
    }

    fun updateTask() {
        viewModelScope.launch {
            taskRepository.updateTask(
                Task(
                    id = taskId ?: "",
                    title = title.value,
                    description = description.value,
                    priority = priority.value,
                    dueDate = dueDate.value
                )
            )
        }
    }
}
