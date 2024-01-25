package com.example.galeria.screens

import androidx.annotation.DrawableRes
import com.example.galeria.R

sealed class Screen(val route: String, @DrawableRes val icon: Int) {
    object PhotoSection: Screen("photo", R.drawable.ic_photo)
    object VideoSection: Screen("video", R.drawable.ic_video)
    object CreateFolderSection: Screen("create_folder", R.drawable.ic_create_folder)
    object LibrarySection: Screen("library", R.drawable.ic_library)
}