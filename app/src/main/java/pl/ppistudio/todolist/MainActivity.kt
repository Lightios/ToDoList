package pl.ppistudio.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.ppistudio.todolist.ui.theme.ToDoListTheme
import pl.ppistudio.todolist.ui.list.TaskListViewModel

class MainActivity : ComponentActivity() {
    val viewModel: TaskListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ToDoListApp).setCurrentActivity(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
