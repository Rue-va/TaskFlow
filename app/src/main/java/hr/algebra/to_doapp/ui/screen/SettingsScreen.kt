package hr.algebra.to_doapp.ui.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Translate // Corrected icon import
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

// --- Locale Helper Function ---
fun setLocaleAndRestart(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val resources = context.resources
    val config = resources.configuration
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        config.setLocale(locale)
    } else {
        config.locale = locale
    }
    resources.updateConfiguration(config, resources.displayMetrics)

    // Restart the activity to apply language changes
    (context as? Activity)?.let {
        it.finish()
        it.startActivity(Intent(it, it.javaClass))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    darkThemeEnabled: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Dark mode toggle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Text("Dark mode")
                Spacer(Modifier.weight(1f))
                Switch(
                    checked = darkThemeEnabled,
                    onCheckedChange = onToggleDarkTheme
                )
            }

            HorizontalDivider()

            // Language Selector Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    Icons.Outlined.Translate,
                    contentDescription = null,
                    Modifier.size(20.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Language:")
                Spacer(Modifier.width(8.dp))
                Box {
                    Button(onClick = { expanded = true }) {
                        Text(selectedLanguage)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("English") },
                            onClick = {
                                selectedLanguage = "English"
                                expanded = false
                                setLocaleAndRestart(context, "en")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Croatian") },
                            onClick = {
                                selectedLanguage = "Croatian"
                                expanded = false
                                setLocaleAndRestart(context, "hr")
                            }
                        )
                    }
                }
            }

            HorizontalDivider()

            // About Section
            Spacer(Modifier.height(24.dp))
            Text(
                "About this app:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
            )
            Text(
                "This is a task to-do list application. Plan your tasks, track your productivity, and use multiple languages.",
                Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}