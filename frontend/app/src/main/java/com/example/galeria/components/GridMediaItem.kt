package com.example.galeria.components

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.galeria.ui.theme.BorderColor

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun GridMediaItem(uri: Uri, isVideo: Boolean, onClick: () -> Unit, onLongClick: () -> Unit) {
    GlideImage(
        model = uri,
        contentDescription = "banana",
        modifier = Modifier
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .height(130.dp)
            .clip(RoundedCornerShape(size = 4.dp))
            .combinedClickable(
                onClick = {
                    onClick()
                },
                onLongClick = {
                    onLongClick()
                }
            ),
        contentScale = ContentScale.Crop
    )
}