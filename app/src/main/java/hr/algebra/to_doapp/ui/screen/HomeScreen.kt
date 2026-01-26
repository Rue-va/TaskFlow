package hr.algebra.to_doapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.algebra.to_doapp.ui.components.AnimatedProfileIcon
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import hr.algebra.to_doapp.ui.components.BottomNavBar
import java.time.LocalDate
import androidx.compose.ui.res.stringResource
import hr.algebra.to_doapp.R
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(navController: NavController, viewModel: TaskViewModel=viewModel ()) {
    // This pulls tasks from your DB via ViewModel
    val tasks by viewModel.tasks.collectAsState()
    val today = java.time.LocalDate.now()
    val tasksToday = tasks.count { it.dueDate == today }


        Scaffold(
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {

                // TopBar
                Row(
                    Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedProfileIcon(navController)
                    Spacer(Modifier.weight(1f))
                    Text(
                        "TaskFlow",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(40.dp))
                }
                // Greeting Section
                Spacer(Modifier.height(8.dp))
                Column(
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMM dd")),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                    Text(
                        text = stringResource(id = R.string.greeting),
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Featured Card section
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column {
                        Image(
                            painter = rememberAsyncImagePainter("https://lh3.googleusercontent.com/aida-public/AB6AXuCyXsDhmp2AsaluQS_jFd6IW1sDwIa0H9JhhARUP153G_EdsU4jSzcilYaUXt-bwDUtEcJUjtLH9MCG-VVo2fpHaiSmbgGJE0jHoPY9GltqYBsDFFcn0i8fk_fn7m3c_XtoWJnrGnZCILwO8u_t99orsrDPDKPdZ58TSzaGzeO7dHAbmkZ39ZxWJBY3K-n2ToHaBdZeDVknASxKeNrduR72wp9CIvwG739o-OeJIxjw9FyvrY-CjW3Atix8z9PbWWw8QiDpDCEYPQ"),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 10f)
                        )
                        Spacer(Modifier.height(12.dp))
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.align(Alignment.CenterHorizontally).size(32.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = R.string.daily_summary),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.height(2.dp))
                        val today = LocalDate.now()
                        val tasksToday = tasks.count { it.dueDate == today }
                        Text(text = stringResource(id = R.string.tasks_today, tasksToday),
                        fontSize = 16.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.align(Alignment.CenterHorizontally))
                        Spacer(Modifier.height(16.dp))
                    }
                }

                Spacer(Modifier.weight(1f))
                // View Tasks button
                Button(
                    onClick = { navController.navigate("tasks") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(56.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.view_tasks),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Icon(Icons.Outlined.ArrowForward, contentDescription = null)
                }
            }
        }
    }
