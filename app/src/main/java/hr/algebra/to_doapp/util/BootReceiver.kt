package hr.algebra.to_doapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Example: Send a notification on boot
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            NotificationUtil.showNotification(context, "Boot Completed", "Welcome back! To-do app is ready.")
        }
    }
}