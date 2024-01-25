package com.example.galeria.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.galeria.screens.CreateFolderScreen
import com.example.galeria.screens.LibraryScreen
import com.example.galeria.screens.VideoScreen

@Composable
fun RootNavGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navController, enterTransition = { EnterTransition.None}, exitTransition = { ExitTransition.None }, startDestination = "mainScreens", modifier = androidx.compose.ui.Modifier.padding(innerPadding)) {
        navigation(
            route = "mainScreens",
            startDestination = "photo"
        ) {
            photoSectionGraph(navController)
            composable("video") { VideoScreen() }
            composable("create_folder") { CreateFolderScreen() }
            composable("library") { LibraryScreen() }
        }
    }
}