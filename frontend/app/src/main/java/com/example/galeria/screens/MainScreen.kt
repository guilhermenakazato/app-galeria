package com.example.galeria.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.galeria.R
import com.example.galeria.navigation.RootNavGraph
import com.example.galeria.ui.theme.GradientOne
import com.example.galeria.ui.theme.GradientTwo
import com.example.galeria.utils.Permissions

val mainScreens = listOf(
    Screen.PhotoSection,
    Screen.VideoSection,
    Screen.CreateFolderSection,
    Screen.LibrarySection
);

@Composable
fun MainScreen() {
    val navController = rememberNavController();

    checkAndRequestPermission()
    Scaffold (bottomBar = {
        BottomNavigation(
            backgroundColor = colorResource(R.color.backgroundColor),
            contentColor = Color.White,
            modifier = Modifier
                .height(80.dp)
                .drawBehind {
                    val borderSize = 4.dp.toPx()
                    drawLine(
                        brush = Brush.linearGradient(listOf(GradientOne, GradientTwo, GradientOne)),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = borderSize
                    )
                },
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            mainScreens.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

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
    }) { innerPadding ->
        RootNavGraph(navController, innerPadding)
    }
}
@Composable
fun checkAndRequestPermission() {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Permissions.multiplePermissions(permissions = listOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO
        ))
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