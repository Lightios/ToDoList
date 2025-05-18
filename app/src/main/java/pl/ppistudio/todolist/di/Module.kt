package pl.ppistudio.todolist.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.ppistudio.todolist.data.TaskRepositoryImpl
import pl.ppistudio.todolist.domain.TaskRepository
import pl.ppistudio.todolist.ui.add_edit.AddEditTaskViewModel
import pl.ppistudio.todolist.ui.details.TaskDetailsViewModel
import pl.ppistudio.todolist.ui.list.TaskListViewModel

val appModule = module {
    single<TaskRepository> { TaskRepositoryImpl() }

    viewModel {
        TaskListViewModel(
            taskRepository = get()
        )
    }

    viewModel {
        AddEditTaskViewModel(
            taskRepository = get(),
        )
    }

    viewModel {
        TaskDetailsViewModel(
            taskRepository = get(),
        )
    }
}