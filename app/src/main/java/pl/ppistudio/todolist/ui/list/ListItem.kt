package pl.ppistudio.todolist.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TaskPriority {
    LOW, MEDIUM, HIGH, URGENT
}

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val completed: Boolean,
    val priority: TaskPriority
)

@Composable
fun TaskItem(
    task: Task,
    onToggleComplete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Priority colors
    val priorityColors = mapOf(
        TaskPriority.LOW to Color(0xFF10B981),      // emerald-500
        TaskPriority.MEDIUM to Color(0xFFF59E0B),   // amber-500
        TaskPriority.HIGH to Color(0xFFF43F5E),     // rose-500
        TaskPriority.URGENT to Color(0xFFDC2626)    // red-600
    )

    // Priority labels
    val priorityLabels = mapOf(
        TaskPriority.LOW to "Low",
        TaskPriority.MEDIUM to "Medium",
        TaskPriority.HIGH to "High",
        TaskPriority.URGENT to "Urgent"
    )

    val priorityColor = priorityColors[task.priority] ?: Color(0xFF10B981)
    val slateGray = Color(0xFF1E293B) // slate-800
    val textColor = Color.White
    val secondaryTextColor = Color.White.copy(alpha = 0.7f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 4.dp,
                    color = priorityColor
                ),
                shape = RoundedCornerShape(8.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = slateGray
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Checkbox
                Checkbox(
                    checked = task.completed,
                    onCheckedChange = { onToggleComplete(task.id) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF38BDF8), // sky-400
                        uncheckedColor = textColor.copy(alpha = 0.6f)
                    )
                )

                // Task details
                Column {
                    Text(
                        text = task.title,
                        color = if (task.completed) textColor.copy(alpha = 0.6f) else textColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = task.description,
                        color = if (task.completed) secondaryTextColor.copy(alpha = 0.4f) else secondaryTextColor,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None
                    )
                }
            }

            // Priority indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Priority label
                Surface(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = priorityColor
                ) {
                    Text(
                        text = priorityLabels[task.priority] ?: "Medium",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Priority icon
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(priorityColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Priority ${priorityLabels[task.priority]}",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview2() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A)), // bg-gray-950
            color = Color(0xFF0F172A) // bg-gray-950
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Preview different priorities
                TaskItem(
                    task = Task(
                        id = "1",
                        title = "Complete project proposal",
                        description = "Finish the quarterly project proposal",
                        completed = false,
                        priority = TaskPriority.HIGH
                    ),
                    onToggleComplete = {}
                )

                TaskItem(
                    task = Task(
                        id = "2",
                        title = "Client meeting preparation",
                        description = "Prepare slides for the client presentation",
                        completed = false,
                        priority = TaskPriority.URGENT
                    ),
                    onToggleComplete = {}
                )

                TaskItem(
                    task = Task(
                        id = "3",
                        title = "Weekly team check-in",
                        description = "Review team progress and blockers",
                        completed = false,
                        priority = TaskPriority.MEDIUM
                    ),
                    onToggleComplete = {}
                )

                TaskItem(
                    task = Task(
                        id = "4",
                        title = "Update documentation",
                        description = "Update the user guide with new features",
                        completed = true,
                        priority = TaskPriority.LOW
                    ),
                    onToggleComplete = {}
                )
            }
        }
    }
}