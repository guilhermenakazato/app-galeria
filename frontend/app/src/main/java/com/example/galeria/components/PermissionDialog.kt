package com.example.galeria.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.galeria.ui.theme.AlertButtonBorder
import com.example.galeria.R

@Composable
fun PermissionDialog(onDismissRequest: () -> Unit, onConfirm: () -> Unit, description: String, buttonText: String, @DrawableRes icon: Int) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            backgroundColor = colorResource(id = R.color.alertBackground),
            modifier = Modifier
                .height(144.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "", Modifier.padding(6.dp)
                )
                Text(
                    description,
                    color = colorResource(id = R.color.textColor),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 15.dp),
                    fontSize = 15.sp
                )
                TextButton(
                    onClick = { onConfirm() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .drawBehind {
                            val borderSize = 1.dp.toPx()
                            drawLine(
                                AlertButtonBorder,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = borderSize
                            )
                        },
                ) {
                    Text(buttonText, color = colorResource(id = R.color.textColor))
                }
            }
        }
    }
}