package com.example.elsol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElSolApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElSolApp() {
    val navController = rememberNavController()
    val drawerState =
        remember { androidx.compose.material3.DrawerState(androidx.compose.material3.DrawerValue.Closed) }
    var favoriteCount by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val solarImages = listOf(
        SolarImage("Corona Solar", R.drawable.corona_solar),
        SolarImage("Erupción Solar", R.drawable.erupcionsolar),
        SolarImage("Espiculas", R.drawable.espiculas),
        SolarImage("Filamentos", R.drawable.filamentos),
        SolarImage("Magnetosfera", R.drawable.magnetosfera),
        SolarImage("Mancha Solar", R.drawable.manchasolar)
    )

    ModalNavigationDrawer(
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(320.dp)
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.erupcionsolar),
                    contentDescription = "Sol",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                NavigationDrawerItem(
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Build,
                                contentDescription = "Build",
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text("Build")
                        }
                    },
                    selected = navController.currentDestination?.route == "build",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigate("build") {
                            popUpTo("build") { inclusive = true }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Info",
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text("Info")
                        }
                    },
                    selected = navController.currentDestination?.route == "info",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigate("info")
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text("Email")
                        }
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    containerColor = Color(0xFFD92222),
                    contentColor = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Abrir menú"
                                )
                            }

                            IconButton(onClick = { favoriteCount++ }) {
                                BadgedBox(
                                    badge = {
                                        if (favoriteCount > 0) {
                                            Badge { Text(favoriteCount.toString()) }
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Favorite, contentDescription = "Favoritos")
                                }
                            }
                        }

                        FloatingActionButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Agregar"
                            )
                        }
                    }
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { padding ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "build",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("build") {
                        BuildScreen(
                            solarImages = solarImages,
                            onShowSnackbar = { message ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(message)
                                }
                            }
                        )
                    }
                    composable("info") {
                        InfoScreen()
                    }
                }
            }
        }
    }
}

data class SolarImage(val name: String, val imageRes: Int)