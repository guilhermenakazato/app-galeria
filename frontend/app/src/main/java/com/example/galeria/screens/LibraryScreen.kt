package com.example.galeria.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.galeria.R
import com.example.galeria.ui.theme.BgGradientOne
import com.example.galeria.ui.theme.BgGradientTwo

@Composable
fun LibraryScreen() {
    val colorStops = arrayOf(
        0.9f to BgGradientOne,
        1f to BgGradientTwo,
    )

    Column(
        modifier = Modifier
            .background(Brush.verticalGradient(colorStops = colorStops))
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(painter = painterResource(R.drawable.logo), contentDescription = "Logo", modifier = Modifier.padding(vertical = 10.dp))
        Text("Biblioteca", fontSize = 30.sp, color = colorResource(R.color.textColor))
    }
}