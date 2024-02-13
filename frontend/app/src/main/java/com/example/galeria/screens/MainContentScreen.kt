package com.example.galeria.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.galeria.R
import com.example.galeria.navigation.MainContentGraph
import com.example.galeria.ui.theme.Black
import com.example.galeria.ui.theme.Modifiers.Companion.bottomBarStyle
import com.example.galeria.utils.Permissions

sealed class Screen(val route: String, @DrawableRes val icon: Int) {
    object PhotoSection: Screen("photo", R.drawable.ic_photo)
    object VideoSection: Screen("video", R.drawable.ic_video)
    object CreateFolderSection: Screen("create_folder", R.drawable.ic_create_folder)
    object LibrarySection: Screen("library", R.drawable.ic_library)
}

val mainScreens = listOf(
    Screen.PhotoSection,
    Screen.VideoSection,
    Screen.CreateFolderSection,
    Screen.LibrarySection
);

@Composable
fun MainContentScreen(rootNavController: NavHostController) {
    val navController = rememberNavController();

    checkAndRequestPermission()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                elevation = 0.dp,
                backgroundColor = Black,
                contentColor = Color.White,
                modifier = Modifier.bottomBarStyle(),
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                mainScreens.forEach { screen ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true

                    val animatedBgColor by animateColorAsState(
                        if (selected) Color.White else colorResource(id = R.color.backgroundColor),
                        label = "bgColor",
                        animationSpec = tween(
                            250, easing = LinearEasing
                        )
                    )

                    val animatedColor by animateColorAsState(
                        if (selected) colorResource(id = R.color.backgroundColor) else Color.White,
                        label = "color",
                        animationSpec = tween(
                            250, easing = LinearEasing
                        )
                    )

                    BottomNavigationItem(
                        icon =
                        {
                            Icon(
                                painterResource(screen.icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .drawBehind {
                                        drawRoundRect(
                                            animatedBgColor,
                                            cornerRadius = CornerRadius(30f, 30f)
                                        )
                                    }
                                    .padding(8.dp),
                                tint = animatedColor
                            )
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        MainContentGraph(navController, rootNavController, innerPadding)
    }
}

@Composable
fun checkAndRequestPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Permissions.multiplePermissions(
            permissions = listOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        )
    } else {
        Permissions.singlePermission(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}

@Composable
fun openAppSettings() {
    val context = LocalContext.current

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts("package", context.packageName, null)
    context.startActivity(intent)
}