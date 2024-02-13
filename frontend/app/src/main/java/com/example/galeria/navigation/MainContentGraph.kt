package com.example.galeria.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.galeria.screens.maincontent.CreateFolderScreen
import com.example.galeria.screens.maincontent.LibraryScreen
import com.example.galeria.screens.maincontent.PhotoScreen
import com.example.galeria.screens.maincontent.VideoScreen

@Composable
fun MainContentGraph(navHostController: NavHostController, rootNavHostController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navHostController,
        startDestination = "photo",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("photo") { PhotoScreen(rootNavHostController) }
        composable("video") { VideoScreen() }
        composable("create_folder") { CreateFolderScreen() }
        composable("library") { LibraryScreen() }
    }
}