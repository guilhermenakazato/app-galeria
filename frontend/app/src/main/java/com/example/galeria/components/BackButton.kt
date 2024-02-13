package com.example.galeria.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.galeria.R
import com.example.galeria.ui.theme.Black

@Composable
fun BackButton(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .width(30.dp)
            .height(30.dp)
            .background(color = Black, shape = RoundedCornerShape(size = 10.dp))
            .padding(start = 3.dp, top = 3.dp, end = 3.dp, bottom = 3.dp),
        onClick = {
            onClick()
        },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "voltar",
            tint = Color.White
        )
    }
}