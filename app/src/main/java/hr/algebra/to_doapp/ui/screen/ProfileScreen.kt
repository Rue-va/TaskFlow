package hr.algebra.to_doapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.algebra.to_doapp.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    var showAbout by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center),
                    tint = Color.White
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "Ruvarashe Lydia MAKUVISE",
                fontSize = 22.sp,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "ruva@email.com",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 15.sp
            )
            Spacer(Modifier.height(24.dp))
            Divider(Modifier.padding(horizontal = 32.dp))
            Spacer(Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = { navController.navigate("settings") }
            ) {
                Icon(Icons.Outlined.Settings, contentDescription = "Settings", Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Settings")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = { showAbout = true }
            ) {
                Icon(Icons.Outlined.Info, contentDescription = "About", Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("About")
            }

            if (showAbout) {
                AlertDialog(
                    onDismissRequest = { showAbout = false },
                    title = { Text("About") },
                    text = { Text("This is a task to-do list application. Keep your daily agenda on track with deadlines and priorities. Switch between English and Croatian in settings!") },
                    confirmButton = {
                        TextButton(onClick = { showAbout = false }) { Text("Close") }
                    }
                )
            }
        }
    }
}