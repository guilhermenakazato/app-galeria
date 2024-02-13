package com.example.galeria.screens.maincontent

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.frontend.model.Image
import com.example.galeria.components.AppBar
import com.example.galeria.components.GridMediaItem
import com.example.galeria.controllers.ImageController
import com.example.galeria.ui.theme.BgGradientOne
import com.example.galeria.ui.theme.BgGradientTwo
import com.example.galeria.ui.theme.Black
import com.example.galeria.ui.theme.Modifiers.Companion.photoGridDragHandler
import com.example.galeria.utils.GroupBy
import com.example.galeria.utils.MediaType
import com.example.galeria.utils.Storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

// TODO: arrumar bagunça :D
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PhotoScreen(
    rootNavHostController: NavHostController
) {
    val colorStops = arrayOf(
        0.9f to BgGradientOne,
        1f to BgGradientTwo,
    )

    val selectedIds = rememberSaveable { mutableStateOf(emptySet<Long>()) }
    val inSelectionMode by remember { derivedStateOf { selectedIds.value.isNotEmpty() } }

    val context = LocalContext.current
    val state = rememberLazyGridState()
    var loading by remember { mutableStateOf(true) }
    var groupedImages by remember {
        mutableStateOf(mapOf<Pair<String, String>, MutableList<Image>>())
    }

    val autoScrollSpeed = remember { mutableFloatStateOf(0f) }
    // Executing the scroll
    LaunchedEffect(autoScrollSpeed.floatValue) {
        if (autoScrollSpeed.floatValue != 0f) {
            while (isActive) {
                state.scrollBy(autoScrollSpeed.floatValue)
                delay(10)
            }
        }
    }

    LaunchedEffect(loading) {
        val imageQueryResult = ImageController.getImages(context)
        val newHashCode = imageQueryResult.first
        val imageList = imageQueryResult.second
        val oldHashCode = Storage.retrieveHash(context, MediaType.image)
        groupedImages = GroupBy.month(imageList)
        loading = false
    }

    if (loading) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Black)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .align(Alignment.Center),
                color = Color.White,
            )
        }
    } else {
        Scaffold(
            Modifier
                .background(Brush.verticalGradient(colorStops = colorStops))
                .statusBarsPadding(),
            topBar = { AppBar(onBackButtonClick = null) }
        ) { _ ->
            Box {
                LazyVerticalGrid(
                    state = state,
                    modifier = Modifier
                        .background(Brush.verticalGradient(colorStops = colorStops))
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .photoGridDragHandler(
                            state,
                            selectedIds,
                            autoScrollSpeed = autoScrollSpeed,
                            autoScrollThreshold = with(LocalDensity.current) { 40.dp.toPx() }
                        ),
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

                        items(images, key = { it.secondaryId }) { image ->
                            val imageUri: Uri = image.uri
                            val selected by remember { derivedStateOf { selectedIds.value.contains(image.secondaryId) } }

                            GridMediaItem(
                                uri = imageUri,
                                isVideo = false,
                                selected = selected,
                                inSelectedMode = inSelectionMode,
                                boxModifier = Modifier.semantics {
                                    if (!inSelectionMode) {
                                        onLongClick("Select") {
                                            selectedIds.value += image.secondaryId
                                            true
                                        }
                                    }
                                }
                                    .then(if (inSelectionMode) {
                                        Modifier.toggleable(
                                            value = selected,
                                            onValueChange = {
                                                if (it) {
                                                    selectedIds.value += image.secondaryId
                                                } else {
                                                    selectedIds.value -= image.secondaryId
                                                }
                                            }
                                        )
                                    } else Modifier.clickable {
                                        rootNavHostController.navigate("fullscreen-photo/${image.primaryId}")
                                    })
                            )
                        }
                    }
                }
            }
        }
    }
}