package hr.algebra.to_doapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AnimatedProfileIcon(navController: NavController) {
    var animated by remember { mutableStateOf(false) }
    var navigate by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (animated) 1.15f else 1f,
        animationSpec = tween(durationMillis = 300),
        finishedListener = {
            if (animated) {
                navigate = true
            }
            animated = false
        }
    )

    if (navigate) {
        LaunchedEffect(navigate) {
            navController.navigate("profile")
            navigate = false
        }
    }

    Icon(
        imageVector = Icons.Outlined.AccountCircle,
        contentDescription = "Profile",
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .size(40.dp)
            .scale(scale)
            .clickable(enabled = !animated) {
                animated = true
            }
    )
}