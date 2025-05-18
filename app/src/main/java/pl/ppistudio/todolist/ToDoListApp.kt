package pl.ppistudio.todolist

import android.app.Activity
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import pl.ppistudio.todolist.di.appModule
import java.lang.ref.WeakReference

class ToDoListApp : Application(){
    private var _currentActivity: WeakReference<Activity>? = null

    val currentActivity: Activity?
        get() = _currentActivity?.get()

    fun setCurrentActivity(activity: Activity) {
        _currentActivity = WeakReference(activity)
    }


    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@ToDoListApp)
            modules(appModule)
        }
    }
}