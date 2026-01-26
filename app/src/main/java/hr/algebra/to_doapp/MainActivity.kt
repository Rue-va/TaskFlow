package hr.algebra.to_doapp
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import hr.algebra.to_doapp.ui.AppNavHost
import hr.algebra.to_doapp.ui.theme.TodoAppTheme
import hr.algebra.to_doapp.util.SettingsUtil
import hr.algebra.to_doapp.util.TaskReminderWorker
import java.util.concurrent.TimeUnit

@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val notifPermission = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            // Optional: show a Toast
            if (isGranted) {
                Toast.makeText(context, "Notifications enabled!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Notifications permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
        LaunchedEffect(Unit) { notifPermission.launch(Manifest.permission.POST_NOTIFICATIONS) }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // WorkManager init OUTSIDE setContent!
        val workRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "task_reminder", ExistingPeriodicWorkPolicy.KEEP, workRequest
        )
        setContent {
            RequestNotificationPermission() // Call it at the top!
            val context = LocalContext.current
            var darkMode by remember { mutableStateOf(SettingsUtil.isDarkMode(context)) }
            TodoAppTheme(darkTheme = darkMode) {
                AppNavHost(
                    onToggleDarkTheme = { enabled ->
                        darkMode = enabled
                        SettingsUtil.saveDarkMode(context, enabled)
                    },
                    darkThemeEnabled = darkMode
                )
            }
        }
    }
}