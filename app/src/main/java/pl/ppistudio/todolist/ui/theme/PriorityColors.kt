package pl.ppistudio.todolist.ui.theme

import androidx.compose.ui.graphics.Color
import pl.ppistudio.todolist.domain.Priority

val PRIORITY_HIGH = Color(0xFFF43F5E)
val PRIORITY_MEDIUM = Color(0xFFF59E0B)
val PRIORITY_LOW = Color(0xFF10B981)

fun priorityToColor(priority: Priority): Color {
    return when (priority) {
        Priority.HIGH -> PRIORITY_HIGH
        Priority.MEDIUM -> PRIORITY_MEDIUM
        Priority.LOW -> PRIORITY_LOW
    }
}