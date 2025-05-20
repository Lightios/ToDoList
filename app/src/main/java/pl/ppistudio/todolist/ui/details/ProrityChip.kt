package pl.ppistudio.todolist.ui.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.ppistudio.todolist.R
import pl.ppistudio.todolist.ToDoListApp
import pl.ppistudio.todolist.domain.Priority
import pl.ppistudio.todolist.ui.theme.ToDoListTheme
import pl.ppistudio.todolist.ui.theme.priorityToColor


@Composable
fun PriorityChip(priority: Priority) {
    val label = when (priority) {
        Priority.HIGH -> "High Priority"
        Priority.MEDIUM -> "Medium Priority"
        Priority.LOW -> "Low Priority"
    }

    val resource = when (priority) {
        Priority.HIGH -> R.drawable.priority_high
        Priority.MEDIUM -> R.drawable.priority_medium
        Priority.LOW -> R.drawable.priority_low
    }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(vertical = 4.dp),
        border = BorderStroke(
            width = 1.dp,
            color = priorityToColor(priority)
        ),

    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = resource),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview
@Composable
private fun PriorityChipPreview() {
    ToDoListTheme  {
        PriorityChip(priority = Priority.HIGH)
    }
}