package com.example.galeria.screens

import android.content.ContentUris
import android.provider.MediaStore
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.galeria.components.BackButton
import com.example.galeria.components.BottomBar
import com.example.galeria.ui.theme.Black
import com.example.galeria.utils.PhoneSystem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ViewFullPhotoScreen(idString: String, rootNavHostController: NavHostController) {
    val id: Long = idString.toLong()
    val view = LocalView.current
    
    var show = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    val uri = ContentUris.withAppendedId(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        id
    )

    if(show.targetState) {
        PhoneSystem(view).Interface().show(WindowInsetsCompat.Type.systemBars())
    } else {
        PhoneSystem(view).Interface().hide(WindowInsetsCompat.Type.systemBars())
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Black),
    ) {
        Column {
            GlideImage(
                model = uri,
                contentDescription = "ooga ooga",
                Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        show.targetState = !show.currentState
                    },
                contentScale = ContentScale.Fit
            )

            AnimatedVisibility(
                visibleState = show,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                BottomBar(80.dp)
            }
        }

        AnimatedVisibility(
            visibleState = show,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            BackButton(
                Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, top = 16.dp)) {
                rootNavHostController.popBackStack()
            }
        }
    }
}

