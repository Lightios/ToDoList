package pl.ppistudio.todolist.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.ppistudio.todolist.domain.Task
import pl.ppistudio.todolist.domain.TaskRepository

class TaskListViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val tasks = taskRepository.tasksFlow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    init {
        viewModelScope.launch {
            taskRepository.getAllTasks()
        }
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

//    init {
//        loadTasks()
//    }

//    fun loadTasks() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            taskRepository.getTasks().let { tasks ->
//                _tasks.value = tasks
//            }
//            _isLoading.value = false
//        }
//    }

    fun updateTaskCompletionStatus(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTaskCompletionStatus(task, isCompleted)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }
}
