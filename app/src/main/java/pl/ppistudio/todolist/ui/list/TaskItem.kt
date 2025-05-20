package pl.ppistudio.todolist.ui.list

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.ppistudio.todolist.R
import pl.ppistudio.todolist.domain.Priority
import pl.ppistudio.todolist.domain.Task
import pl.ppistudio.todolist.ui.theme.ToDoListTheme
import pl.ppistudio.todolist.ui.theme.priorityToColor
import java.util.Date
import java.util.Locale


@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val color = priorityToColor(task.priority)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(
                BorderStroke(
                    width = 0.dp,
                    color = color
                ),
                shape = RoundedCornerShape(8.dp)
            )
        ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,

        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .height(40.dp)
                    .width(8.dp),
                shape = RoundedCornerShape(4.dp),
                color = color
            ) { }

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (task.isCompleted)
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    else
                        MaterialTheme.colorScheme.onSurface
                )

                if (task.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                task.dueDate?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                                .format(Date(it)),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            val (source, description) = when (task.priority) {
                Priority.HIGH -> {
                    Pair(
                        painterResource(id = R.drawable.priority_high),
                        "High priority"
                    )
                }
                Priority.MEDIUM -> {
                    Pair(
                        painterResource(id = R.drawable.priority_medium),
                        "Medium priority"
                    )

                }
                Priority.LOW -> {
                    Pair(
                        painterResource(id = R.drawable.priority_low),
                        "Low priority"
                    )
                }
            }

            Image(
                painter = source,
                contentDescription = description,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(40.dp),
                colorFilter = null
            )
        }
    }
}


@Preview
@Composable
private fun TaskItemPreview() {
    ToDoListTheme(darkTheme = true) {

        TaskItem(
            task = Task(
                id = "1",
                title = "Task 1",
                description = "Description 1",
                isCompleted = true,
                dueDate = null,
                priority = Priority.HIGH
            ),
            onClick = {},
            onCheckedChange = {}
        )
    }
}