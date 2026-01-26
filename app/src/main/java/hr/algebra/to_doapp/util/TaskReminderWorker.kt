package hr.algebra.to_doapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class TaskReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        NotificationUtil.showNotification(
            applicationContext,
            "Task Reminder",
            "Check your tasks for today!"
        )
        return Result.success()
    }
}