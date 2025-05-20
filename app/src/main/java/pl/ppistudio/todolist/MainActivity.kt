package pl.ppistudio.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.ppistudio.todolist.data.AppDatabase
import pl.ppistudio.todolist.ui.list.TaskItem
import pl.ppistudio.todolist.ui.theme.ToDoListTheme
import pl.ppistudio.todolist.ui.list.TaskListViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ToDoListApp).setCurrentActivity(this)
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}