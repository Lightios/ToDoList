package pl.ppistudio.todolist.ui.add_edit
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.ppistudio.todolist.domain.Priority
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskId: String?,
    title: String,
    description: String,
    priority: Priority,
    dueDate: Long?,
    isLoading: Boolean,
    showDatePicker: Boolean,
    loadTask: (String) -> Unit,
    setShowDatePicker: (Boolean) -> Unit,
    setDueDate: (Long?) -> Unit,
    onGoBack: () -> Unit,
    setTitle: (String) -> Unit,
    setDescription: (String) -> Unit,
    setPriority: (Priority) -> Unit,
    createTask: () -> Unit,
    updateTask: () -> Unit
) {

    val isEditMode = taskId != null

    LaunchedEffect(taskId) {
        if (isEditMode) {
            loadTask(taskId!!)
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = {
//                viewModel.setShowDatePicker(false)
                setShowDatePicker(false)
            },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
//                        viewModel.setDueDate(it)
                        setDueDate(it)
                    }
//                    viewModel.setShowDatePicker(false)
                    setShowDatePicker(false)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
//                        viewModel.setShowDatePicker(false)
                    setShowDatePicker(false)
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Task" else "Add Task") },
                navigationIcon = {
                    IconButton(onClick = {
//                        navController.popBackStack()
                        onGoBack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
//                        viewModel.setTitle(it)
                        setTitle(it)
                    },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        setDescription(it)
                    },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Priority",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PriorityOption(
                        priority = Priority.LOW,
                        selected = priority == Priority.LOW,
                        onClick = {
                            setPriority(Priority.LOW)
                          },
                        modifier = Modifier.weight(1f)
                    )

                    PriorityOption(
                        priority = Priority.MEDIUM,
                        selected = priority == Priority.MEDIUM,
                        onClick = {
                            setPriority(Priority.MEDIUM)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    PriorityOption(
                        priority = Priority.HIGH,
                        selected = priority == Priority.HIGH,
                        onClick = {
                            setPriority(Priority.HIGH)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Due Date",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    if (dueDate != null) {
                        TextButton(onClick = {
                            setDueDate(null)
                        }
                        ) {
                            Text("Clear")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
//                        viewModel.setShowDatePicker(true)
                        setShowDatePicker(true)
                              },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = dueDate?.let {
                            SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
                                .format(Date(it))
                        } ?: "Select Due Date",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        if (isEditMode) {
                            updateTask()
                        } else {
                            createTask()
                        }
                        onGoBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.isNotBlank()
                ) {
                    Text(if (isEditMode) "Update Task" else "Create Task")
                }
            }
        }
    }
}


data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
