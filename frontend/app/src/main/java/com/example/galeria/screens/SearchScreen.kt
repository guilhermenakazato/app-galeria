package com.example.galeria.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.galeria.R

@Composable
fun SearchScreen() {
    Column(
        modifier = Modifier
            .background(colorResource(R.color.backgroundColor))
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(painter = painterResource(R.drawable.logo), contentDescription = "Logo", modifier = Modifier.padding(vertical = 10.dp))
        Text("Pesquisar", fontSize = 30.sp, color = colorResource(R.color.textColor)) }
}