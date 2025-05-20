package pl.ppistudio.todolist.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.ppistudio.todolist.domain.Priority
import pl.ppistudio.todolist.domain.Task
import pl.ppistudio.todolist.domain.TaskRepository

class TaskDetailsViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    var task: StateFlow<Task?> = MutableStateFlow(null)

//    private val _task = MutableStateFlow<Task?>(null)
//    val task: StateFlow<Task?> = _task

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadTask(taskId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            task = taskRepository.tasksFlow.map { tasks ->
                tasks.find { it.id == taskId }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                null
            )
            _isLoading.value = false
        }
    }

    fun updateTaskCompletionStatus(isCompleted: Boolean) {
        viewModelScope.launch {
//            taskRepository.updateTask(task.value!!.copy(isCompleted = isCompleted))
            taskRepository.updateTaskCompletionStatus(task.value!!, isCompleted)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }

}
