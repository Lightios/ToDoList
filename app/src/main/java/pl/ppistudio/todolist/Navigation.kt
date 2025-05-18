package pl.ppistudio.todolist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable
import pl.ppistudio.todolist.ui.list.TaskListScreen
import pl.ppistudio.todolist.ui.add_edit.AddEditTaskScreen
import org.koin.androidx.compose.getViewModel
import pl.ppistudio.todolist.ui.add_edit.AddEditTaskViewModel
import pl.ppistudio.todolist.ui.details.TaskDetailsScreen
import pl.ppistudio.todolist.ui.details.TaskDetailsViewModel
import pl.ppistudio.todolist.ui.list.TaskListViewModel


@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = TaskListScreen,
        modifier = modifier
    ) {
        composable<TaskListScreen> {
            val viewModel = getViewModel<TaskListViewModel>()
            val tasks = viewModel.tasks.collectAsStateWithLifecycle()
            val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()

            TaskListScreen(
                onTaskClick = { task ->
//                    navController.navigate(AddEditTaskScreen(taskId = task.id))
                    navController.navigate(TaskDetailsScreen(taskId = task.id))
                },
                onTaskCheckedChange = { task, changed ->
                    viewModel.updateTaskCompletionStatus(task.id, changed)
                },
                onTaskDelete = { taskId ->
                    viewModel.deleteTask(taskId)
                },
                tasks = tasks.value,
                isLoading = isLoading.value,
            )
        }

        composable<AddEditTaskScreen> {
            val viewModel = getViewModel<AddEditTaskViewModel>()
            val taskId = it.arguments?.getString("taskId")

            AddEditTaskScreen(
                taskId = taskId,
                title = viewModel.title.collectAsStateWithLifecycle().value,
                description = viewModel.description.collectAsStateWithLifecycle().value,
                priority = viewModel.priority.collectAsStateWithLifecycle().value,
                dueDate = viewModel.dueDate.collectAsStateWithLifecycle().value,
                isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value,
                showDatePicker = viewModel.showDatePicker.collectAsStateWithLifecycle().value,
                loadTask = { taskID ->
                    viewModel.loadTask(taskID)
                },
                setShowDatePicker = { show ->
                    viewModel.setShowDatePicker(show)
                },

                setDueDate = { date ->
                    viewModel.setDueDate(date)
                },
                onGoBack = {
                    navController.navigate(TaskListScreen) {
                        popUpTo(TaskListScreen) {
                            inclusive = true
                        }
                    }
                },
                setTitle = { title ->
                    viewModel.setTitle(title)
                },
                setDescription = { description ->
                    viewModel.setDescription(description)
                },
                setPriority = { priority ->
                    viewModel.setPriority(priority)
                },
                createTask = {
                    viewModel.createTask()
                },
                updateTask = {
                    viewModel.updateTask()
                }
            )
        }

        composable<TaskDetailsScreen> {
            val viewModel = getViewModel<TaskDetailsViewModel>()
            val taskId = it.arguments?.getString("taskId") ?: ""
            val task = viewModel.task.collectAsStateWithLifecycle()
            val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

            TaskDetailsScreen(
                taskId = taskId,
                loadTask = { taskID ->
                    viewModel.loadTask(taskID)
                },
                onGoBack = {
                    navController.navigate(TaskListScreen) {
                        popUpTo(TaskListScreen) {
                            inclusive = true
                        }
                    }
                },

                taskState = task,
                isLoading = isLoading,
                onEditTask = {
                    navController.navigate(AddEditTaskScreen(taskId = taskId))
                }

            )
        }
    }
}

/*

    setShowDatePicker: (Boolean) -> Unit,
    setDueDate: (Long?) -> Unit,
    onGoBack: () -> Unit,
    setTitle: (String) -> Unit,
    setDescription: (String) -> Unit,
    setPriority: (Priority) -> Unit,
    creteTask: () -> Unit,
    updateTask: () -> Unit

 */


@Serializable
object TaskListScreen

@Serializable
data class AddEditTaskScreen(
    val taskId: String? = null
)

@Serializable
data class TaskDetailsScreen(
    val taskId: String
)