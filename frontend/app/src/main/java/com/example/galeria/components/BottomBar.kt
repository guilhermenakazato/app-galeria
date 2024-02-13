package com.example.galeria.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.galeria.R
import com.example.galeria.ui.theme.BottomBarIconColor
import com.example.galeria.ui.theme.Modifiers.Companion.bottomBarStyle

sealed class Action(@DrawableRes val icon: Int, val actionFunction: Unit) {
    object Delete: Action(R.drawable.delete, delete())
    object Move: Action(R.drawable.move_to, moveTo())
    object Share: Action(R.drawable.share, share())
}

val actions = listOf(
    Action.Delete,
    Action.Move,
    Action.Share
)

@Composable
fun BottomBar(height: Dp) {
    Row(
        modifier = Modifier
            .bottomBarStyle(height = height)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        actions.forEach {
            IconButton(onClick = {
                it.actionFunction
            }) {
                Icon(painter = painterResource(id = it.icon), contentDescription = "", tint = BottomBarIconColor)
            }
        }
    }
}

fun delete() {

}

fun moveTo() {

}

fun share() {

}