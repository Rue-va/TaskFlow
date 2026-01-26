package hr.algebra.to_doapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import hr.algebra.to_doapp.ui.screen.*

@Composable
fun AppNavHost(
    onToggleDarkTheme: (Boolean) -> Unit,
    darkThemeEnabled: Boolean
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("calendar") {
            CalendarScreen(navController)
        }
        composable("tasks") {
            TaskListScreen(
                navController = navController,
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("settings") {
            SettingsScreen(
                onBack = { navController.popBackStack() } ,
                darkThemeEnabled = darkThemeEnabled,
                onToggleDarkTheme = onToggleDarkTheme
            )
        }
    }
}