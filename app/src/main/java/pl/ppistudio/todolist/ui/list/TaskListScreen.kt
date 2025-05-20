package pl.ppistudio.todolist.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.ppistudio.todolist.domain.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    onTaskClick: (Task) -> Unit,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    onTaskDelete: (String) -> Unit,
    onTaskAdd: () -> Unit,
    tasks: List<Task>,
    isLoading: Boolean,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Tasks",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onTaskAdd,
//                onClick = { navController.navigate(AddEditTaskDestination.createRouteWithParam(null)) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (tasks.isEmpty()) {
            EmptyTasksPlaceholder(modifier = Modifier.padding(padding))
            Box{
                Text(
                    text = "No tasks yet",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        } else {
            TaskList(
                tasks = tasks,
                onTaskClick = { task ->
//                    navController.navigate(TaskDetailsDestination.createRouteWithParam(task.id))
                    onTaskClick(task)
                },
                onTaskCheckedChange = { task, isCompleted ->
//                    viewModel.updateTaskCompletionStatus(task.id, isCompleted)
                    onTaskCheckedChange(task, isCompleted)
                },
                onTaskDelete = { task ->
//                    viewModel.deleteTask(task.id)
                    onTaskDelete(task.id)
               },

                modifier = Modifier.padding(padding)
            )
        }
    }
}


@Preview
@Composable
private fun TaskListScreenPreview() {
    
}