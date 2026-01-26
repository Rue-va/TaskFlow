package hr.algebra.to_doapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomBarTab(val label: String, val icon: ImageVector, val route: String)

private val bottomTabs = listOf(
    BottomBarTab("Home", Icons.Outlined.Home, "home"),
    BottomBarTab("Calendar", Icons.Outlined.DateRange, "calendar"),
    BottomBarTab("Tasks", Icons.Outlined.Check, "tasks"),
    BottomBarTab("Profile", Icons.Outlined.Person, "profile")
)

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        bottomTabs.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = { navController.navigate(tab.route) { launchSingleTop = true } },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) }
            )
        }
    }
}