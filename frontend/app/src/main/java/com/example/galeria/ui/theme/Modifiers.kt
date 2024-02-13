package com.example.galeria.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toIntRect

class Modifiers {
    companion object {
        fun Modifier.bottomBarStyle(height: Dp = 80.dp, margin: Dp = 0.dp): Modifier {
            return this then Modifier
                .padding(margin)
                .height(height)
                .background(Black)
                .drawBehind {
                    val borderSize = 4.dp.toPx()
                    drawLine(
                        brush = Brush.linearGradient(
                            listOf(
                                BackgroundGradientOne,
                                BackgroundGradientTwo,
                                BackgroundGradientOne
                            )
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = borderSize
                    )
                }
                .navigationBarsPadding()
        }

        fun Modifier.photoGridDragHandler(
            lazyGridState: LazyGridState,
            selectedIds: MutableState<Set<Long>>,
            autoScrollSpeed: MutableState<Float>,
            autoScrollThreshold: Float
        ) = pointerInput(Unit) {
            var initialKey: Long? = null
            var currentKey: Long? = null

            detectDragGesturesAfterLongPress(
                onDragStart = { offset ->
                    lazyGridState.gridItemKeyAtPosition(offset)?.let { key ->
                        if (!selectedIds.value.contains(key)) {
                            initialKey = key
                            currentKey = key
                            selectedIds.value = selectedIds.value + key
                        }
                    }
                },
                onDragCancel = { initialKey = null; autoScrollSpeed.value = 0f },
                onDragEnd = { initialKey = null; autoScrollSpeed.value = 0f },
                onDrag = { change, _ ->
                    if (initialKey != null) {
                        val distFromBottom =
                            lazyGridState.layoutInfo.viewportSize.height - change.position.y
                        val distFromTop = change.position.y
                        autoScrollSpeed.value = when {
                            distFromBottom < autoScrollThreshold -> autoScrollThreshold - distFromBottom
                            distFromTop < autoScrollThreshold -> -(autoScrollThreshold - distFromTop)
                            else -> 0f
                        }

                        lazyGridState.gridItemKeyAtPosition(change.position)?.let { key ->
                            if (currentKey != key) {
                                selectedIds.value = selectedIds.value
                                    .minus(initialKey!!..currentKey!!)
                                    .minus(currentKey!!..initialKey!!)
                                    .plus(initialKey!!..key)
                                    .plus(key..initialKey!!)
                                currentKey = key
                            }
                        }
                    }
                }
            )
        }

        fun LazyGridState.gridItemKeyAtPosition(hitPoint: Offset): Long? =
            layoutInfo.visibleItemsInfo.find { itemInfo ->
                itemInfo.size.toIntRect().contains(hitPoint.round() - itemInfo.offset)
            }?.key as? Long
    }
}


