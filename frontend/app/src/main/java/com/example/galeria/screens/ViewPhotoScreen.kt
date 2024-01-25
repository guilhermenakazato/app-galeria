package com.example.galeria.screens

import android.content.ContentUris
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.galeria.components.BackButton
import com.example.galeria.ui.theme.Black

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ViewPhotoScreen(idString: String, navHostController: NavHostController) {
    val id: Long = idString.toLong()

    val uri = ContentUris.withAppendedId(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        id
    )

    Box(
        modifier = Modifier
            .background(Black)
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        GlideImage(
            model = uri,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            contentDescription = "ooga ooga",
            contentScale = ContentScale.Fit
        )

        BackButton(
            onClick = {
                navHostController.popBackStack()
            },
            modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
        )
    }
}