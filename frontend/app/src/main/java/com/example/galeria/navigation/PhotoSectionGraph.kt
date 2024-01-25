package com.example.galeria.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.galeria.screens.PhotoScreen
import com.example.galeria.screens.ViewPhotoScreen

fun NavGraphBuilder.photoSectionGraph(
    navHostController: NavHostController
) {
    navigation(route = "photo", startDestination = "photo-initial") {
        composable("photo-initial") { PhotoScreen(navHostController) }
        composable(
            "photo-view/{id}",
            arguments = listOf(
                navArgument("id") {
                    NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if(id != null) {
                ViewPhotoScreen(id, navHostController)
            }
        }
    }
}