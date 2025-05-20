package pl.ppistudio.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.ppistudio.todolist.domain.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): TaskDao
}



