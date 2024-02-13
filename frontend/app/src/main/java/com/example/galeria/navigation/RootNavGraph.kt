package com.example.galeria.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.galeria.screens.MainContentScreen
import com.example.galeria.screens.ViewFullPhotoScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "main-content",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable("main-content") { MainContentScreen(navController) }
        composable(
            "fullscreen-photo/{id}",
            listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")

            if (id != null) {
                ViewFullPhotoScreen(idString = id, rootNavHostController = navController)
            }
        }
    }
}
