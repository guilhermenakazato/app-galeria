package com.example.galeria.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.galeria.components.AppBar
import com.example.galeria.components.GridMediaItem
import com.example.galeria.controllers.ImageController
import com.example.galeria.ui.theme.BgGradientOne
import com.example.galeria.ui.theme.BgGradientTwo
import com.example.galeria.utils.GroupBy

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PhotoScreen(
    navHostController: NavHostController
) {
    val colorStops = arrayOf(
        0.9f to BgGradientOne,
        1f to BgGradientTwo,
    )

    val context = LocalContext.current
    val imageList = ImageController.getImages(context)
    val groupedImages = GroupBy.month(imageList)
    val state = rememberLazyGridState()

    Scaffold(
        Modifier.background(Brush.verticalGradient(colorStops = colorStops)),
        topBar = {AppBar(onBackButtonClick = null)}
    ) { _ ->
        LazyVerticalGrid(
            state = state,
            modifier = Modifier
                .background(Brush.verticalGradient(colorStops = colorStops))
                .fillMaxWidth()
                .fillMaxHeight(),
            columns = GridCells.Adaptive(104.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            groupedImages.forEach { (date, images) ->
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    Text(
                        "${date.first} de ${date.second}",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp, 8.dp, 0.dp, 0.dp)
                    )
                }

                items(images) { image ->
                    val imageUri: Uri = image.uri
                    GridMediaItem(
                        uri = imageUri,
                        isVideo = false,
                        onClick = {
                            navHostController.navigate("photo-view/${image.id}")
                        },
                        onLongClick = {

                        }
                    )
                }
            }
        }
    }
}