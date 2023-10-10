package com.example.galeria.screens

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.frontend.model.Image
import com.example.frontend.model.Video
import com.example.galeria.R
import com.example.galeria.components.PermissionDialog
import com.example.galeria.ui.theme.GradientOne
import com.example.galeria.ui.theme.GradientTwo
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

sealed class Screen(val route: String, @DrawableRes val icon: Int) {
    object Photo: Screen("photo", R.drawable.ic_photo)
    object Video: Screen("video", R.drawable.ic_video)
    object CreateFolder: Screen("create_folder", R.drawable.ic_create_folder)
    object Search: Screen("search", R.drawable.ic_search)
    object Library: Screen("library", R.drawable.ic_library)
}

val items = listOf(
    Screen.Photo,
    Screen.Video,
    Screen.CreateFolder,
    Screen.Search,
    Screen.Library
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
                }
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
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
                                    drawRoundRect(animatedBgColor, cornerRadius = CornerRadius(30f, 30f))
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
        NavHost(navController = navController, enterTransition = {EnterTransition.None}, exitTransition = { ExitTransition.None }, startDestination = "photo", modifier = Modifier.padding(innerPadding)) {
            composable("photo") {PhotoScreen()}
            composable("video") {VideoScreen()}
            composable("create_folder") {CreateFolderScreen()}
            composable("search") {SearchScreen()}
            composable("library") {LibraryScreen()}
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun checkAndRequestPermission() {
    val readMediaPermissionState = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    if (readMediaPermissionState.status.isGranted) {
        getMediaQuantity()
    } else {
        val confirmed = remember { mutableStateOf(false) }
        val openDialog = remember { mutableStateOf(true) }

        if(readMediaPermissionState.status.shouldShowRationale) {
            if(openDialog.value) {
                PermissionDialog(
                    onDismissRequest = {  },
                    onConfirm = { confirmed.value = true },
                    description = "Você recusou a permissão, para continuar utilizando a Galeria permita o acesso.",
                    buttonText = "Configurações",
                    icon = R.drawable.ic_alert
                )
            }

            if(confirmed.value) {
                openAppSettings()
                confirmed.value = false
            }
        } else {
            SideEffect {
                readMediaPermissionState.launchPermissionRequest()
            }
        }
    }
}

@Composable
fun openAppSettings() {
    val context = LocalContext.current

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts("package", context.packageName, null)
    context.startActivity(intent)
}

@Composable
fun getMediaQuantity() {
    val mediaQuantity = getVideosQuantity() + getImagesQuantity()
    Log.i("heyo", mediaQuantity.toString())
}

@Composable
fun getVideosQuantity() : Int {
    val videoList = mutableListOf<Video>()

    val collection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }

    val projection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.SIZE
    )

    val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

    val context = LocalContext.current
    val query = context.contentResolver.query(
        collection,
        projection,
        null,
        null,
        sortOrder
    )
    query?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        val nameColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
        val durationColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
        val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val name = cursor.getString(nameColumn)
            val duration = cursor.getInt(durationColumn)
            val size = cursor.getInt(sizeColumn)

            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                id
            )

            videoList += Video(contentUri, name, size, duration)
        }

        cursor.close()
    }

    return videoList.size
}

@Composable
fun getImagesQuantity() : Int {
    val imageList = mutableListOf<Image>()

    val collection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.SIZE
    )

    val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"
    val context = LocalContext.current

    val query = context.contentResolver.query(
        collection,
        projection,
        null,
        null,
        sortOrder
    )
    query?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val nameColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
        val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val name = cursor.getString(nameColumn)
            val size = cursor.getInt(sizeColumn)

            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                id
            )

            imageList += Image(contentUri, name, size)
        }

        cursor.close()
    }

    return imageList.size
}