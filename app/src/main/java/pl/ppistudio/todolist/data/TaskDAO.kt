package pl.ppistudio.todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pl.ppistudio.todolist.domain.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>


    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)

    @Query("UPDATE task SET is_completed = :isCompleted WHERE id = :taskId")
    fun update(taskId: String, isCompleted: Boolean)
}