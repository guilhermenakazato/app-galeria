package com.example.galeria.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.galeria.ui.theme.BorderColor
import com.example.galeria.ui.theme.BorderGradientOne
import com.example.galeria.ui.theme.BorderGradientTwo
import com.example.galeria.ui.theme.checkedBoxColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GridMediaItem(
    uri: Uri,
    isVideo: Boolean,
    selected: Boolean,
    inSelectedMode: Boolean,
    boxModifier: Modifier
) {
    val gradientBorder = listOf(
        BorderGradientOne,
        BorderGradientTwo
    )

    Box(
        modifier = boxModifier
    ) {
        GlideImage(
            model = uri,
            contentDescription = "banana",
            modifier = Modifier
                .padding(8.dp)
                .border(
                    width = if(selected) 3.dp else 1.dp,
                    brush = if(selected) Brush.verticalGradient(gradientBorder) else SolidColor(BorderColor),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .height(130.dp)
                .clip(RoundedCornerShape(size = 4.dp))
                .drawWithContent {
                    drawContent()

                    if(inSelectedMode && !selected) {
                        drawRect(
                            Color.Black,
                            alpha = 0.6f
                        )
                    }

                },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .background(
                    if(inSelectedMode && !selected) Color.Red
                    else Color.White
                )
                .zIndex(100f)
        )

        if(inSelectedMode) {
            Checkbox(
                checked = selected,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = checkedBoxColor,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .background(Color.Black)
                    .width(16.dp)
                    .height(16.dp)
            )
        }
    }
}